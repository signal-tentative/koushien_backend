package com.example.koushien_backend.service

import com.example.koushien_backend.model.Reaction

interface ReactionService {
    fun getReaction(): List<Reaction?>
}