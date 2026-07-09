package com.example.koushien_backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.koushien_backend.model.Reaction

@Repository
interface ReactionRepository: JpaRepository<Reaction,Long>{
    fun findAllById(id: Long): kotlin.collections.List<com.example.koushien_backend.model.Reaction?>
}