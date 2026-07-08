package com.example.koushien_backend.service

import com.example.koushien_backend.controler.RequestUser
import com.example.koushien_backend.model.User

interface UserService {
    fun getUsers(): List<User>
    fun updateUser(id: Long,request: RequestUser): User
    fun deleteUser(id:Long):User
    fun createUser(request: RequestUser): User
}