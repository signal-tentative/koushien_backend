package com.example.koushien_backend.service

import com.example.koushien_backend.model.Script
import com.example.koushien_backend.repository.ScriptRepository
import org.springframework.stereotype.Service

@Service
class SqlScriptService(val scriptRepository: ScriptRepository) : ScriptService {
    override fun getScripts(): List<Script?> {
        return scriptRepository.findAll()
    }
}