package com.example.koushien_backend.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.koushien_backend.model.Script

@Repository
interface ScriptRepository : JpaRepository<Script, String> {
    fun findAllById(ids: Long): kotlin.collections.List<com.example.koushien_backend.model.Script?>
}