package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Qta
import com.example.koushien_backend.service.Q_taService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Q_taControler(val qtaService: Q_taService){
    @GetMapping("/qtas")
    fun getQta(): List<Qta?>{
        return qtaService.getQta()
    }
}