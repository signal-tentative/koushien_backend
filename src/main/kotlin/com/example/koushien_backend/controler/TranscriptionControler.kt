package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Transcription
import  com.example.koushien_backend.service.TranscriptionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TranscriptionControler(val transcriptionService: TranscriptionService) {
    @GetMapping("/transcription")
    fun getTranscription(): List<Transcription?>{
        return transcriptionService.getTranscription()
    }
}