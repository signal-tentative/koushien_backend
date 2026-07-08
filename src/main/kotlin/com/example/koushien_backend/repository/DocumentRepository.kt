package com.example.koushien_backend.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.koushien_backend.model.Document

@Repository
interface DocumentRepository : JpaRepository<Document, Long> {}