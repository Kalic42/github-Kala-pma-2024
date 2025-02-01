package com.example.strongerme


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.strongerme.data.AppDatabase
import com.example.strongerme.data.ExerciseStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class ExercisePlanActivity : AppCompatActivity() {

    private lateinit var btnComplete: Button
    private lateinit var btnIncomplete: Button
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_plan)

        db = AppDatabase.getDatabase(applicationContext)

        val rvExercises = findViewById<RecyclerView>(R.id.rvExercises)
        btnComplete = findViewById(R.id.btnSuccess)
        btnIncomplete = findViewById(R.id.btnFail)

        val difficulty = intent.getStringExtra("difficulty") ?: "easy"
        val exercises = getExercisesForDifficulty(difficulty)


        rvExercises.layoutManager = LinearLayoutManager(this)
        rvExercises.adapter = ExerciseAdapter(exercises) { allChecked ->
            btnComplete.isEnabled = allChecked
            btnComplete.setTextColor(if (allChecked) Color.WHITE else Color.LTGRAY)
            btnComplete.setBackgroundColor(if (allChecked) Color.GREEN else Color.GRAY)
        }

        btnComplete.isEnabled = false
        btnComplete.setTextColor(Color.LTGRAY)
        btnComplete.setBackgroundColor(Color.GRAY)


        btnComplete.setOnClickListener {
            setExerciseStatus("Splněno")
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }


        btnIncomplete.setOnClickListener {
            setExerciseStatus("Nesplněno")
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setExerciseStatus(status: String) {
        val currentDate = getCurrentDate()
        val isCompleted = status == "Splněno"

        CoroutineScope(Dispatchers.IO).launch {
            val existingStatus = db.exerciseStatusDao().getStatusByDate(currentDate)

            if (existingStatus != null) {
                val updatedStatus = existingStatus.copy(isCompleted = isCompleted)
                db.exerciseStatusDao().insertOrUpdate(updatedStatus)
            } else {
                val newStatus = ExerciseStatus(date = currentDate, isCompleted = isCompleted)
                db.exerciseStatusDao().insertOrUpdate(newStatus)
            }
        }
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        return "$day.$month.$year"
    }

    private fun getExercisesForDifficulty(difficulty: String): List<Exercise> {
        return when (difficulty) {
            "easy" -> listOf(
                Exercise("Dřepy", "Proveďte 20 dřepů s vlastní vahou", 2),
                Exercise("Kliky", "Proveďte 10 kliků z koleny na zemi", 2),
                Exercise("Plank", "Držte 30 vteřin plank s koleny na zemi", 2),
                Exercise("Výpady", "Proveďte 10 výpadů na každou nohu s vlastní vahou", 2),
                Exercise("Kroužení pažemi", "Proveďte kroužení pažemi na každou stranu pro zlepšení mobility", 2)
            ).shuffled().take(3)

            "medium" -> listOf(
                Exercise("Dřepy najedné noze", "Proveďte 15 dřepů na každé noze s přidržením", 2),
                Exercise("Kliky", "Proveďte 30 kliků s nataženými nohami", 2),
                Exercise("Plank", "Držte 60 vteřin plank s nataženými nohami", 2),
                Exercise("Výpady", "Výpady s většími pohyby", 2),
                Exercise("Mountain Climbers", "Proveďte Mountain Climbers", 2),
                Exercise("Jumping Jacks", "Skákejte s ruce i nohama", 2),
                Exercise("Ruské twisty", "Provádějte ruské twisty pro zpevnění středu těla", 2), // Přidán nový cvik
                Exercise("Bicepsové zdvihy", "Proveďte bicepsové zdvihy pro posílení paží", 2), // Přidán nový cvik
                Exercise("Výpony na lýtka", "Proveďte výpony na lýtka pro posílení dolních končetin", 2) // Přidán nový cvik
            ).shuffled().take(6) // Náhodně vybere 6 cviků z 9

            "hard" -> listOf(
                Exercise("Dřepy s váhou", "Přidejte váhu pro náročnější dřepy", 2),
                Exercise("Kliky s nohama zvednutýma", "Provádějte kliky s nohama zvednutýma", 2),
                Exercise("Plank s nohama zvednutýma", "Udržujte plank s nohama zvednutýma", 2),
                Exercise("Burpees", "Maximální intenzita Burpees", 2),
                Exercise("Skákání přes švihadlo", "Skákání přes švihadlo s rychlým tempem", 2),
                Exercise("Pull-ups", "Proveďte pull-upy na hrazdě", 2),
                Exercise("Squat Jumps", "Skákání do dřepu", 2),
                Exercise("Push-ups", "Provádějte push-upy na plný rozsah", 2),
                Exercise("Dřepy s výskokem", "Skákejte po každém dřepu", 2),
                Exercise("Deadlifts", "Proveďte mrtvé tahy pro posílení celého těla", 2), // Přidán nový cvik
                Exercise("Plank s rotací", "Udržujte plank s rotací těla pro zpevnění trupu", 2), // Přidán nový cvik
                Exercise("Skákání do dřepu", "Proveďte skákání do dřepu pro zlepšení výbušnosti", 2), // Přidán nový cvik
                Exercise("Sklapovačky", "Proveďte sklapovačky pro zpevnění břicha", 2), // Přidán nový cvik
                Exercise("Kettlebell swings", "Použijte kettlebell pro trénink celého těla", 2) // Přidán nový cvik
            ).shuffled().take(9) // Náhodně vybere 9 cviků z 14

            else -> emptyList()
        }
    }
}

