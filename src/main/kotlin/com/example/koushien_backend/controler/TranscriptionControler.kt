package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Transcript
import com.example.koushien_backend.repository.DocumentRepository
import com.example.koushien_backend.repository.LectureRepository
import com.example.koushien_backend.repository.TranscriptRepository
import  com.example.koushien_backend.service.TranscriptService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
@RestController
class TranscriptionControler(val transcriptionService: TranscriptService,val lectureRepository: LectureRepository,val documentRepository: DocumentRepository,val transcriptRepository: TranscriptRepository) {
    @GetMapping("/transcriptions")
    fun getTranscript(): List<Transcript?>{
        return transcriptionService.getTranscript()
    }
    @PostMapping("/transcriptions")
    fun saveTranscript(
        @RequestParam("lecture_id") lecture_id: Long,
        @RequestParam("document_id") document_id: Long,
        @RequestParam("page") page: Int,
        @RequestParam("transcript") transcript: String,
        @RequestParam("time") time: LocalDateTime,
    ): ResponseEntity<Transcript> {
        val savedLecture = lectureRepository.findByIdOrNull(lecture_id)?: return ResponseEntity.badRequest().body(Transcript())
        println("1")
        val savedDocument = documentRepository.findByIdOrNull(document_id)?: return ResponseEntity.badRequest().body(Transcript())
        println("2")
        val saveTranscript = Transcript(
            page = page,
            transcript = transcript,
            time = time,
            lecture = savedLecture,
            document = savedDocument
        )
        val createdTranscript = transcriptRepository.save(saveTranscript)
        return ResponseEntity.ok(createdTranscript)
    }
}