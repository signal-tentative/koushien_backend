package com.example.koushien_backend.service

import com.example.koushien_backend.model.Insight
import com.example.koushien_backend.repository.InsightRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SqlInsightSetvice(val insightRepository: InsightRepository) : InsightService {
    override fun getInsights(): List<Insight?> {
        return insightRepository.findAll()
    }

    override fun getInsightById(id: Long): List<Insight?> {
        return  insightRepository.findAllById(id)
    }
}