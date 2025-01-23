package com.example.student_assignment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.student_assignment.model.Model
import com.example.student_assignment.model.Student

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        title = "Edit Student Data"

        val nameField: EditText = findViewById(R.id.edit_student_name)
        val idField: EditText = findViewById(R.id.edit_student_id)
        val phoneField: EditText = findViewById(R.id.edit_student_phone)
        val addressField: EditText = findViewById(R.id.edit_student_address)
        val checkBox: CheckBox = findViewById(R.id.edit_student_checked)
        val saveButton: Button = findViewById(R.id.save_button)
        val deleteButton: Button = findViewById(R.id.delete_button)
        val cancelButton: Button = findViewById(R.id.cancel_button)

        val originalId = intent.getStringExtra("studentId") ?: ""
        val name = intent.getStringExtra("studentName")
        val id = intent.getStringExtra("studentId")
        val phone = intent.getStringExtra("studentPhone")
        val address = intent.getStringExtra("studentAddress")
        val isChecked = intent.getBooleanExtra("studentChecked", false)

        nameField.setText(name)
        idField.setText(id)
        phoneField.setText(phone)
        addressField.setText(address)
        checkBox.isChecked = isChecked

        saveButton.setOnClickListener {
            val updatedName = nameField.text.toString()
            val updatedId = idField.text.toString()
            val updatedPhone = phoneField.text.toString()
            val updatedAddress = addressField.text.toString()
            val updatedChecked = checkBox.isChecked

            val students = Model.shared.students
            val studentIndex = students.indexOfFirst { it.id == originalId }
            if (studentIndex != -1) {
                students[studentIndex] = Student(
                    name = updatedName,
                    id = updatedId,
                    avatarUrl = "",
                    phone = updatedPhone,
                    address = updatedAddress,
                    isChecked = updatedChecked
                )
            }

            val intent = Intent()
            intent.putExtra("studentName", updatedName)
            intent.putExtra("studentId", updatedId)
            intent.putExtra("studentPhone", updatedPhone)
            intent.putExtra("studentAddress", updatedAddress)
            intent.putExtra("studentChecked", updatedChecked)
            setResult(Activity.RESULT_OK, intent)

            finish()
        }

        deleteButton.setOnClickListener {
            val students = Model.shared.students
            students.removeIf { it.id == originalId }

            val intent = Intent()
            intent.putExtra("isDeleted", true)
            setResult(Activity.RESULT_OK, intent)

            finish()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}
