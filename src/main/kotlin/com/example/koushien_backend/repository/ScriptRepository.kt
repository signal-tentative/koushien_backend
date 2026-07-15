package com.example.koushien_backend.repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.example.koushien_backend.model.Script
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

@Repository
interface ScriptRepository : JpaRepository<Script, String> {
    @Query(
        value = """SELECT * FROM script WHERE document_id = :document_id ;""",
        nativeQuery = true
    )
    fun findAllById(
        @Param("documentId") document_id: Long,
    ): kotlin.collections.List<com.example.koushien_backend.model.Script?>
}