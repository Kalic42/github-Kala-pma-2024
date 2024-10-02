package com.example.myapp002myownlinearlayout

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val etNumber1 = findViewById<EditText>(R.id.etNumber1)
        val etNumber2 = findViewById<EditText>(R.id.etNumber2)
        val tvInformation = findViewById<TextView>(R.id.tvInformation)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        // Nastavení obsluhy pro tlačítko Odeslat
        btnCalculate.setOnClickListener {
            // Převod zadaných hodnot na čísla (pokud jsou platná)
            val number1 = etNumber1.text.toString().toIntOrNull()
            val number2 = etNumber2.text.toString().toIntOrNull()

            if (number1 != null && number2 != null) {
                // Sečtení čísel A (number1) a B (number2)
                val sum = number1 + number2

                // Zobrazení textu v TextView
                val formattedText = "Číslo A je $number1, číslo B je $number2 a jejich součet je $sum."
                tvInformation.text = formattedText
            } else {
                // Pokud jedno z čísel není platné, zobrazí chybovou zprávu
                tvInformation.text = "Zadejte platná čísla pro A (Number1) a B (Number2)."
            }
        }

        // Nastavení obsluhy pro tlačítko Smazat
        btnDelete.setOnClickListener {
            etNumber1.text.clear()
            etNumber2.text.clear()
            tvInformation.text = ""
        }
    }
}