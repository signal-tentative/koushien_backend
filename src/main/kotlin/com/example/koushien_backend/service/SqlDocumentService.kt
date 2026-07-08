package com.example.koushien_backend.service

import com.example.koushien_backend.model.Document
import com.example.koushien_backend.repository.DocumentRepository
import org.springframework.stereotype.Service

@Service
class SqlDocumentService(val documentRepository: DocumentRepository): DocumentService {
    override fun getDocuments(): List<Document?> {
     return  documentRepository.findAll()
    }
}