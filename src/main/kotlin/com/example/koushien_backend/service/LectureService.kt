package com.example.koushien_backend.service
import com.example.koushien_backend.model.Lecture
import com.example.koushien_backend.controler.RequestLecture

interface LectureService {
    fun getLecture(): List<Lecture?>
    fun getAll(): List<Lecture>
    fun createdOrder(request: RequestLecture): Lecture
    fun deleteOrder(id: Long): Lecture
    fun updateOrder(id:Long,request: RequestLecture): Lecture
}