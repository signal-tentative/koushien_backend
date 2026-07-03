package com.example.koushien_backend.repository

import com.example.koushien_backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User,Long>{
    fun existsByUid(uid: String): Boolean
}