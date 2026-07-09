package com.example.koushien_backend.controler
import com.example.koushien_backend.dataclass.chat
import com.example.koushien_backend.service.ServiceClass
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
open class ChatController(private val service: ServiceClass){
    //    @CrossOrigin(origins = [""])
    //ローカルで使うときは上をlcoalhost8082にして
    @PostMapping("/")
    suspend fun getAnswer(@RequestBody request: chat): String {
        println(request.ques)
        println(request.img)
        val getAnswer = service.chat(request.ques,request.img)
        return getAnswer
    }
}