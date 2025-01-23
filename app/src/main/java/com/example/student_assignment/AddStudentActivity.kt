package com.example.student_assignment

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.student_assignment.model.Model
import com.example.student_assignment.model.Student

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)

        title = "Add New Student"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameField: EditText = findViewById(R.id.add_student_name_text_field)
        val idField: EditText = findViewById(R.id.add_student_id_text_field)
        val phoneField: EditText = findViewById(R.id.add_student_phone_text_field)
        val addressField: EditText = findViewById(R.id.add_student_address_text_field)
        val checkBox: CheckBox = findViewById(R.id.add_student_check_box)
        val saveButton: Button = findViewById(R.id.add_student_save_button)
        val cancelButton: Button = findViewById(R.id.add_student_cancel_button)
        val successMessage: TextView = findViewById(R.id.add_student_success_saved_text_view)

        saveButton.setOnClickListener {
            val name = nameField.text.toString()
            val id = idField.text.toString()
            val phone = phoneField.text.toString()
            val address = addressField.text.toString()
            val isChecked = checkBox.isChecked

            if (name.isEmpty() || id.isEmpty()) {
                successMessage.text = "Name and ID are required."
                successMessage.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                return@setOnClickListener
            }

            val newStudent = Student(name, id, null, phone, address, isChecked)
            Model.shared.students.add(newStudent)

            successMessage.text = "Student added successfully!"
            successMessage.setTextColor(resources.getColor(android.R.color.holo_green_dark))

            finish()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}
