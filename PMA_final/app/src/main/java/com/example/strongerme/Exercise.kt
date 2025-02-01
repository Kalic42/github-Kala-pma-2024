package com.example.strongerme

data class Exercise(
    val name: String,
    val description: String,
    val sets: Int,
    var isCompleted: Boolean = false
)
