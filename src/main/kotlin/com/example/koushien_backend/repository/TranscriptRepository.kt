package com.example.koushien_backend.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.koushien_backend.model.Transcript
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Repository
interface TranscriptRepository: JpaRepository<Transcript, Long>{
//    fun findAllById(id: Long): kotlin.collections.List<com.example.koushien_backend.model.Transcript?>

    @Query(
        value = """SELECT * FROM transcripts WHERE lecture_id = :lecture_id AND time >= CAST(:time AS timestamp)- INTERVAL '40 seconds';""",
        nativeQuery = true
    )
    fun findByTimeTrump(
        @Param("lecture_id") lecture_id: Long,
        @Param("time") time: LocalDateTime,
    ): kotlin.collections.List<Transcript?>

}

