package com.example.stopwatch

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Chronometer
import android.widget.NumberPicker
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    var isRunning=false
    private var minutes:String?="00.00.00"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
var lapselist=ArrayList<String>()
        var arrayAdapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lapselist)
        binding.listview.adapter=arrayAdapter
        binding.lap.setOnClickListener{
            if(isRunning){
                Log.d("size",lapselist.size.toString())
                lapselist.add(binding.chronometer.text.toString())
                arrayAdapter.notifyDataSetChanged()
            }
        }
        binding.clock.setOnClickListener{
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog)
            var numPicker= dialog.findViewById<NumberPicker>(R.id.numberpicker)

            numPicker.minValue=0
            numPicker.maxValue=5
            dialog.findViewById<Button>(R.id.set_time).setOnClickListener{
                minutes=numPicker.value.toString()
                binding.clocktime.text=dialog.findViewById<NumberPicker>(R.id.numberpicker).value.toString()+" mins"
                dialog.dismiss()
            }
            dialog.show()

        }
        binding.run.setOnClickListener{
            if(!isRunning) {
                binding.run.text="Stop"
                isRunning = true;
                if (!minutes.equals("00.00.00")) {
                    binding.chronometer.start()
                    var totalmin = minutes!!.toInt() * 60 * 1000L
                    binding.chronometer.base = SystemClock.elapsedRealtime()
                    binding.chronometer.format = "%S %S"
                    binding.chronometer.onChronometerTickListener =
                        Chronometer.OnChronometerTickListener {
                            var elapsedTime =
                                SystemClock.elapsedRealtime() - binding.chronometer.base
                            if (elapsedTime >= totalmin) {
                                binding.chronometer.stop()
                                isRunning = false
                                binding.run.text = "Run"
                            }
                        }


                }


            }
            else{
                binding.chronometer.stop()
                isRunning=false;
                binding.run.text="Run"
            }

        }
    }
}


