package com.example.koushien_backend.controler
import com.example.koushien_backend.model.DontKnow
import com.example.koushien_backend.service.DontknowService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

//分からないボタン
@RestController
class DontknowControler(val dontknowService: DontknowService) {
    @GetMapping("/dontknows")
    fun getDontKnow():List<DontKnow?>{
        return dontknowService.getDontKnow();
    }
}