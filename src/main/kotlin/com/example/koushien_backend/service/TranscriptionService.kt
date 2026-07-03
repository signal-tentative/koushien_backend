package com.example.koushien_backend.service

import com.example.koushien_backend.model.Transcription

interface TranscriptionService {
    fun getTranscription(): List<Transcription?>
}
