package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Document
import com.example.koushien_backend.model.Lecture
import com.example.koushien_backend.model.User
import com.example.koushien_backend.repository.DocumentRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import com.example.koushien_backend.repository.LectureRepository
import com.example.koushien_backend.service.DocumentService
import com.example.koushien_backend.service.LectureService
import com.example.koushien_backend.service.S3Service
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.apache.coyote.Request
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDateTime
import kotlin.String

@RestController
class DocumentController(
    val documentService: DocumentService,
    val documentRepository: DocumentRepository,
    val lectureRepository: LectureRepository,
    private val s3Service: S3Service
) {
    @PostMapping("/documents")
    fun fileUpload(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("lecture_id") lectureId: Long
    ): ResponseEntity<Document> {
        val linkUrl = s3Service.uploadFile(file)
        val relationLecture = lectureRepository.findByIdOrNull(lectureId)
            ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val document = Document(
            link = linkUrl,
            page = 0,
            lecture = relationLecture
        )
        val savedDocument = documentRepository.save(document)
        return ResponseEntity.ok(savedDocument)
    }
}
