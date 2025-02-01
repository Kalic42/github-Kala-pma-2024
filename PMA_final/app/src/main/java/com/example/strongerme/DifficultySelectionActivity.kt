package com.example.strongerme


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DifficultySelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty_selection)


        val btnEasy = findViewById<Button>(R.id.btnEasy)
        val btnMedium = findViewById<Button>(R.id.btnMedium)
        val btnHard = findViewById<Button>(R.id.btnHard)

        btnEasy.setOnClickListener {
            navigateToExercisePlan("easy")
        }

        btnMedium.setOnClickListener {
            navigateToExercisePlan("medium")
        }

        btnHard.setOnClickListener {
            navigateToExercisePlan("hard")
        }
    }

    private fun navigateToExercisePlan(difficulty: String) {
        Log.d("DifficultySelection", "Selected difficulty: $difficulty")

        saveDifficulty(difficulty)

        val intent = Intent(this, ExercisePlanActivity::class.java).apply {
            putExtra("difficulty", difficulty)
        }
        startActivity(intent)
        finish()
    }

    private fun saveDifficulty(difficulty: String) {
        val sharedPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        sharedPrefs.edit().apply {
            putString("difficulty", difficulty)
            apply()
        }
    }
}
