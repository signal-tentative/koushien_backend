package com.example.koushien_backend.service
import com.example.koushien_backend.model.Student
import org.springframework.stereotype.Service

interface StudentService {
    fun getStudents(): List<Student?>
}