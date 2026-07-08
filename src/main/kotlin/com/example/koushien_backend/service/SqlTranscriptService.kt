package com.example.koushien_backend.service

import  com.example.koushien_backend.model.Transcript
import com.example.koushien_backend.repository.TranscriptRepository
import org.springframework.stereotype.Service

@Service
class SqlTranscriptService(val transcriptRepository: TranscriptRepository): TranscriptService{
    override fun getTranscript(): List<Transcript?>{
        return transcriptRepository.findAll()
    }
}