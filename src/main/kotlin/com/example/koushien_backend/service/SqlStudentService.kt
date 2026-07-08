package com.example.koushien_backend.service

import  com.example.koushien_backend.model.Student
import com.example.koushien_backend.repository.StudentRepository
import org.apache.coyote.Request
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class  SqlStudentService(val studentRepository: StudentRepository) : StudentService {
    override fun getStudents(): List<Student?>{
        return studentRepository.findAll()
    }
}
