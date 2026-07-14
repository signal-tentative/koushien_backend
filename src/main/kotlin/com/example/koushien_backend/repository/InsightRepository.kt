package com.example.koushien_backend.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.koushien_backend.model.Insight
import com.example.koushien_backend.model.Transcript
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

@Repository
interface InsightRepository : JpaRepository<Insight, Long> {
    fun findAllById(id: Long): kotlin.collections.List<com.example.koushien_backend.model.Insight?>

    @Query(
        value = """SELECT * FROM insights WHERE lecture_id = :lecture_id ;""",
        nativeQuery = true
    )
    fun findByAllByLectureId(
        @Param("lecture_id") lecture_id: Long,
    ): kotlin.collections.List<Insight?>
}