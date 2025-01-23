package com.example.student_assignment.model

data class Student(
    val name: String,
    val id: String,
    val avatarUrl: String?,
    val phone: String?,
    val address: String?,
    var isChecked: Boolean?
)