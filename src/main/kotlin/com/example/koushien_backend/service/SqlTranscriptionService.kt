package com.example.koushien_backend.service

import  com.example.koushien_backend.model.Transcription
import com.example.koushien_backend.repository.TranscriptionRepository
import  com.example.koushien_backend.service.TranscriptionService
import org.springframework.stereotype.Service

@Service
class SqlTranscriptionService(val transcriptionRepository: TranscriptionRepository): TranscriptionService{
    override fun getTranscription(): List<Transcription?>{
        return transcriptionRepository.findAll()
    }
}