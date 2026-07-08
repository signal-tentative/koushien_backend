package com.example.koushien_backend.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.koushien_backend.model.Student

@Repository
interface StudentRepository : JpaRepository<Student, Long>{
    fun findByUserUid(uid: String): List<Student?>
}