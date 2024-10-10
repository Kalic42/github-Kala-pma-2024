package com.example.myapp004obejdnavka

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp004obejdnavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        //binding settings
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Objednávka bruslí"

        binding.btnObjednavka.setOnClickListener{

            //načtení ID vybraného radioButtonu z radioGroup
            val brusleId = binding.rbBrusle.checkedRadioButtonId

            val brusle = findViewById<RadioButton>(brusleId)

            val Noze = binding.cbNoze.isChecked
            val Tkanicky = binding.cbTkanicky.isChecked
            val Chranice = binding.cbChranice.isChecked

            val objednavkaText = "Souhrn objednávky: " +
                    "${brusle.text}" +
                         (if(Noze) "; Karbonové nože" else "") +
                         (if(Tkanicky) "; Voskované tkaničky" else "") +
                         (if(Chranice) "; Chrániče na brusle" else "")

            binding.tvObjednavka.text = objednavkaText
        }

//Změna obrázku v závislosti na vybraném radioButtonu

        binding.rbBrusle1.setOnClickListener {
            binding.ivBrusle.setImageResource(R.drawable.supreme)
        }
        binding.rbBrusle2.setOnClickListener {
            binding.ivBrusle.setImageResource(R.drawable.vapor)
        }
        binding.rbBrusle3.setOnClickListener {
            binding.ivBrusle.setImageResource(R.drawable.nexus)
        }

    }
}




