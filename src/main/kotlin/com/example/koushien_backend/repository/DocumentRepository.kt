package com.example.koushien_backend.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.koushien_backend.model.Document
import com.example.koushien_backend.model.Insight
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface DocumentRepository : JpaRepository<Document, Long> {

    @Query(
        value = """SELECT * FROM documents WHERE lecture_id = :lecture_id ;""",
        nativeQuery = true
    )
    fun findByLectureId(
        @Param("lecture_id") lecture_id: Long,
    ): Document?
}