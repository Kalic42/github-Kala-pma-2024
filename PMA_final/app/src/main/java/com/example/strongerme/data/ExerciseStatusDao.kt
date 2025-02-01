package com.example.strongerme.data

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExerciseStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(exerciseStatus: ExerciseStatus)

    @Query("SELECT * FROM exercise_status WHERE date = :date LIMIT 1")
    suspend fun getStatusByDate(date: String): ExerciseStatus?

    @Query("UPDATE exercise_status SET isCompleted = :isCompleted WHERE date = :date")
    suspend fun updateStatus(date: String, isCompleted: Boolean) {
        Log.d("ExerciseStatusDao", "Updating status for date: $date, completed: $isCompleted")
}
}


