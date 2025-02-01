package com.example.strongerme

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val etName = findViewById<EditText>(R.id.etName)
        val etAge = findViewById<EditText>(R.id.etAge)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val age = etAge.text.toString().toIntOrNull()
            val height = etHeight.text.toString().toFloatOrNull()
            val weight = etWeight.text.toString().toFloatOrNull()

            if (name.isEmpty() || age == null || height == null || weight == null) {
                Toast.makeText(this, "Vyplň všechna pole", Toast.LENGTH_SHORT).show()
            } else {

                val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                with(sharedPrefs.edit()) {
                    putString("name", name)
                    putInt("age", age)
                    putFloat("height", height)
                    putFloat("weight", weight)
                    putBoolean("isRegistered", true)
                    apply()
                }

                startActivity(Intent(this, CalendarActivity::class.java))
                finish()
            }
        }
    }
}
