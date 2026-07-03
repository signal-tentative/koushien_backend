package com.example.koushien_backend.service
import  com.example.koushien_backend.model.DontKnow
import com.example.koushien_backend.controler.RequestLecture
import com.example.koushien_backend.model.Qta
import com.example.koushien_backend.repository.DontknowRepository
import com.example.koushien_backend.repository.Q_taRepository
import  com.example.koushien_backend.service.LectureService
import org.springframework.stereotype.Service

@Service
class SqlQ_taService(val qtaRepository: Q_taRepository):Q_taService{
    override fun getQta(): List<Qta?> {
        return qtaRepository.findAll()
    }
}


