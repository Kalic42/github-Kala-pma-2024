package com.example.strongerme


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ExerciseAdapter(
    private val exercises: List<Exercise>,
    private val onExerciseChecked: (Boolean) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    private val checkedExercises = BooleanArray(exercises.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentExercise = exercises[position]
        holder.exerciseName.text = currentExercise.name
        holder.exerciseDescription.text = currentExercise.description

        holder.checkbox.isChecked = checkedExercises[position]

        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            checkedExercises[position] = isChecked
            onExerciseChecked(checkedExercises.all { it })
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseName: TextView = itemView.findViewById(R.id.tvExerciseName)
        val exerciseDescription: TextView = itemView.findViewById(R.id.tvExerciseDescription)
        val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
    }
}
