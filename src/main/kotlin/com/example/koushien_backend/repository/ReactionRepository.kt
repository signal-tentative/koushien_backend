package com.example.koushien_backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.koushien_backend.model.Reaction
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

@Repository
interface ReactionRepository: JpaRepository<Reaction,Long>{
    @Query(
        value = """SELECT * FROM reactions WHERE lecture_id = :lecture_id AND time >= CAST(:time AS timestamp)- INTERVAL '40 seconds';""",
        nativeQuery = true
    )
    fun findAllById(
        @Param("lecture_id") lecture_id: Long,
        @Param("time") time: LocalDateTime,
    ): kotlin.collections.List<com.example.koushien_backend.model.Reaction?>
}