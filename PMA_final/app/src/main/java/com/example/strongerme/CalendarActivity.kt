package com.example.strongerme

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.strongerme.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class CalendarActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLocale(this, "cs")
        setContentView(R.layout.activity_calendar)

        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isRegistered = sharedPrefs.getBoolean("isRegistered", false)

        if (!isRegistered) {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }

        db = AppDatabase.getDatabase(applicationContext)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val statusTextView = findViewById<TextView>(R.id.statusTextView)
        val btnStartExercise = findViewById<Button>(R.id.btnStartExercise)
        val btnShowUserInfo = findViewById<Button>(R.id.btnShowUserInfo)

        val userName = sharedPrefs.getString("name", "Není nastaveno")
        val userAge = sharedPrefs.getInt("age", 0)
        val userHeight = sharedPrefs.getFloat("height", 0f)
        val userWeight = sharedPrefs.getFloat("weight", 0f)

        btnShowUserInfo.setOnClickListener {
            val popupMenu = PopupMenu(this, btnShowUserInfo)
            popupMenu.menuInflater.inflate(R.menu.user_info_menu, popupMenu.menu)

            popupMenu.menu.getItem(0).title = "Jméno: $userName"
            popupMenu.menu.getItem(1).title = "Věk: $userAge"
            popupMenu.menu.getItem(2).title = "Výška: $userHeight cm"
            popupMenu.menu.getItem(3).title = "Váha: $userWeight kg"

            popupMenu.show()
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth.${month + 1}.$year"
            loadExerciseStatus(selectedDate, statusTextView)
        }

        calendarView.setDate(System.currentTimeMillis(), true, true)

        btnStartExercise.setOnClickListener {
            val intent = Intent(this, DifficultySelectionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private fun loadExerciseStatus(date: String, statusTextView: TextView) {
        CoroutineScope(Dispatchers.IO).launch {
            val status = db.exerciseStatusDao().getStatusByDate(date)

            withContext(Dispatchers.Main) {
                if (status != null) {
                    val exerciseStatus = if (status.isCompleted) "Splněno" else "Nesplněno"
                    statusTextView.text = "Pro tento den máte: $exerciseStatus"
                } else {
                    statusTextView.text = "Pro tento den jste ještě necvičili"
                }
            }
        }
    }
}