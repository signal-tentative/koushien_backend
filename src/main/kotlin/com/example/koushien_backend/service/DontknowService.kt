package com.example.koushien_backend.service

import com.example.koushien_backend.model.DontKnow

interface DontknowService {
    fun getDontKnow(): List<DontKnow?>
}