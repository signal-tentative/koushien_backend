package com.example.koushien_backend.service

import com.example.koushien_backend.model.Insight

interface InsightService {
    fun getInsights(): List<Insight?>
    fun getInsightById(id: Long): List<Insight?>
}