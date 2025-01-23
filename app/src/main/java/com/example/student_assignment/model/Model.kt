package com.example.student_assignment.model

class Model private constructor() {
    val students: MutableList<Student> = ArrayList()
    companion object {
        val shared = Model()
    }
    init {
        for (i in 0..5) {
            val student = Student(
                name = "Lior Palatnik $i",
                id = i.toString(),
                avatarUrl = "",
                phone = "0506707400",
                address = "kakakaka",
                isChecked = false
            )
            students.add(student)
        }
    }
}