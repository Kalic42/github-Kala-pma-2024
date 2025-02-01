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
                Exercise("Výpady", "Proveďte 20 výpadů s výskokem při výměně nohou", 2),
                Exercise("Sedy-lehy", "Proveďte 20 sedů-lehů se zapřenými nohami", 2),
                Exercise("Panák", "skákejte panáka 2 minuty v kuse", 2),
                Exercise("Skok přes švihadlo", "2 minuty v kuse skákejte přes švihadlo", 2),
                Exercise("Běh", "Proveďte 5 minutový běh", 2),
                Exercise("Výpony na lýtka", "Proveďte 20 výponů na lýtka", 2)
            ).shuffled().take(6)

            "hard" -> listOf(
                Exercise("Dřepy s váhou", "proveďte 30 dřepů s přidanou váhou", 2),
                Exercise("Kliky se zvednutou nohou", "Proveďte 15 kliků se zvednutou nohou, poté vystřídejte", 2),
                Exercise("Plank se zvednutou nohou", "Držte 60 vteřin plank se zvednutou nohou, poté vystřídejte", 2),
                Exercise("Výpady", "Proveďte 30 výpadů s přidanou váhou", 2),
                Exercise("Skákání přes švihadlo", "Skákejte přes švihadlo s rychlým tempem", 2),
                Exercise("přítahy", "Proveďte 20 přítahů na hrazdě", 2),
                Exercise("Dřepy s výskokem", "Proveďte 50 dřepů s výskokem", 2),
                Exercise("Dřepy na jedné noze", "Proveďte 20 dřepů na každé noze", 2),
                Exercise("Plank na boku", "Držte plank opřený a loket s nohami na zemi", 2),
                Exercise("Biceps zdvihy", "Proveďte 20 zdvihů na biceps se závažím", 2),
                Exercise("Výskoky na bednu", "Proveďte 20 výskoků na bednu či vyšší předmět", 2),
                Exercise("Zadní výpady", "Proveďte 30 výpadů s pokládáním nohou za sebe", 2),
                Exercise("Sklapovačky", "Proveďte 40 sklapovaček pro zpevnění břicha", 2),
                Exercise("Muscle up", "Proveďte muscle up na hrazdě", 2)
            ).shuffled().take(9)

            else -> emptyList()
        }
    }
}

