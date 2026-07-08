package com.example.koushien_backend.repository

import com.example.koushien_backend.model.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureRepository: JpaRepository<Lecture,Long>{
    fun findLectureByCode(code:String): Lecture?
    fun findLectureByUserUid(uid: String): List<Lecture?>
    fun deleteLectureByCode(code: String)
}