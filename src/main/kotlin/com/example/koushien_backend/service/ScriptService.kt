package com.example.koushien_backend.service

import com.example.koushien_backend.model.Script

interface ScriptService {
    fun getScripts(): List<Script?>
}