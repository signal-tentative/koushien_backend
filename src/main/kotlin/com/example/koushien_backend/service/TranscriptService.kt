package com.example.koushien_backend.service

import com.example.koushien_backend.model.Transcript

interface TranscriptService {
    fun getTranscript(): List<Transcript?>
}
