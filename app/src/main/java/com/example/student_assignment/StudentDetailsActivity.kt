package com.example.student_assignment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.student_assignment.model.Model

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var studentId: String
    private lateinit var checkBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Student Details"

        studentId = intent.getStringExtra("studentId") ?: ""

        checkBox = findViewById(R.id.student_checked)

        loadStudentDetails()

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            updateStudentCheckedState(isChecked)
        }

        findViewById<Button>(R.id.edit_button).setOnClickListener {
            val student = Model.shared.students.find { it.id == studentId }
            if (student != null) {
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra("studentName", student.name)
                intent.putExtra("studentId", student.id)
                intent.putExtra("studentPhone", student.phone)
                intent.putExtra("studentAddress", student.address)
                intent.putExtra("studentChecked", student.isChecked ?: false)
                startActivityForResult(intent, 1)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val isDeleted = data?.getBooleanExtra("isDeleted", false) ?: false
            if (isDeleted) {
                finish()
            } else {
                val updatedName = data?.getStringExtra("studentName") ?: ""
                val updatedId = data?.getStringExtra("studentId") ?: ""
                val updatedPhone = data?.getStringExtra("studentPhone") ?: ""
                val updatedAddress = data?.getStringExtra("studentAddress") ?: ""
                val updatedChecked = data?.getBooleanExtra("studentChecked", false) ?: false

                findViewById<TextView>(R.id.student_name).text = "Name: $updatedName"
                findViewById<TextView>(R.id.student_id).text = "ID: $updatedId"
                findViewById<TextView>(R.id.student_phone).text = "Phone: $updatedPhone"
                findViewById<TextView>(R.id.student_address).text = "Address: $updatedAddress"
                checkBox.isChecked = updatedChecked
            }
        }
    }

    private fun loadStudentDetails() {
        val student = Model.shared.students.find { it.id == studentId }
        if (student != null) {
            findViewById<TextView>(R.id.student_name).text = "Name: ${student.name}"
            findViewById<TextView>(R.id.student_id).text = "ID: ${student.id}"
            findViewById<TextView>(R.id.student_phone).text = "Phone: ${student.phone}"
            findViewById<TextView>(R.id.student_address).text = "Address: ${student.address}"
            checkBox.isChecked = student.isChecked ?: false
        }
    }

    private fun updateStudentCheckedState(isChecked: Boolean) {
        val student = Model.shared.students.find { it.id == studentId }
        if (student != null) {
            student.isChecked = isChecked
        }
    }
}
