package com.example.koushien_backend.service

import com.example.koushien_backend.model.Document

interface DocumentService {
    fun getDocuments(): List<Document?>
}