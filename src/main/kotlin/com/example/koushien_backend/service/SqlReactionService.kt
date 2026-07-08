package com.example.koushien_backend.service
import  com.example.koushien_backend.model.Reaction
import com.example.koushien_backend.repository.ReactionRepository
import org.springframework.stereotype.Service

@Service
class SqlReactionService(val reactionRepository: ReactionRepository): ReactionService {
    override fun getReaction(): List<Reaction?> {
       return reactionRepository.findAll()
    }
}