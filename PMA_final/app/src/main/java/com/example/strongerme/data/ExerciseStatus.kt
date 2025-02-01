package com.example.strongerme.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_status")
data class ExerciseStatus(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val isCompleted: Boolean
)