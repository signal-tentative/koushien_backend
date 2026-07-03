package com.example.koushien_backend.service

import com.example.koushien_backend.model.Qta

interface Q_taService {
    fun getQta(): List<Qta?>
}