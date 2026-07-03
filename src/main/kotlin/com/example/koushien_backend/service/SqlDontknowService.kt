package com.example.koushien_backend.service
import  com.example.koushien_backend.model.DontKnow
import com.example.koushien_backend.controler.RequestLecture
import com.example.koushien_backend.repository.DontknowRepository
import  com.example.koushien_backend.service.LectureService
import org.springframework.stereotype.Service

@Service
class SqlDontknowService( val dontknowRepository: DontknowRepository): DontknowService {
    override fun getDontKnow(): List<DontKnow?> {
       return dontknowRepository.findAll()
    }
}