package com.example.koushien_backend.service
interface Services {
    suspend fun chat(ques:String,  img: String): String
}