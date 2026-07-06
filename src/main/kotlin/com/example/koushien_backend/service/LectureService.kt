package com.example.koushien_backend.service
import com.example.koushien_backend.model.Lecture
import com.example.koushien_backend.controler.RequestLecture

interface LectureService {
    fun getLectures(): List<Lecture?>
    fun getLectureById(lectureId: Long): Lecture?
    fun updateLecture(lectureId: Long,request: RequestLecture): Lecture?
}