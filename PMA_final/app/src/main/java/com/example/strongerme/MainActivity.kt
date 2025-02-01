package com.example.strongerme


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btnStart)


        btnStart.setOnClickListener { _: View? ->
            val intent = Intent(this@MainActivity, CalendarActivity::class.java)

            startActivity(intent)
        }
    }
}
