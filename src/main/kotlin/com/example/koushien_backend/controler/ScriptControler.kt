package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Script
import com.example.koushien_backend.model.User
import com.example.koushien_backend.repository.DocumentRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import com.example.koushien_backend.repository.LectureRepository
import com.example.koushien_backend.repository.ScriptRepository
import com.example.koushien_backend.service.LectureService
import com.example.koushien_backend.service.S3Service
import com.example.koushien_backend.service.ScriptService
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.apache.coyote.Request
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDateTime
import kotlin.String

@RestController
class ScriptControler(val scriptService: ScriptService,val scriptRepository: ScriptRepository,val documentRepository: DocumentRepository) {
    @GetMapping("/scripts")
    fun getScripts():List<Script?>{
        return scriptService.getScripts()
    }
    @PostMapping("/scripts")
    fun addScript(
        @RequestParam("document_id") documentId: Long,
        @RequestParam("page") page: Int,
        @RequestParam("script")script:String,
    ):ResponseEntity<Script>
    {
        val savedDocument = documentRepository.findByIdOrNull(documentId) ?: return ResponseEntity.badRequest().build()

        val savedScript = Script(
            page = page,
            script = script,
            document = savedDocument,
        )

        val createScript = scriptRepository.save(savedScript)
        return ResponseEntity.ok().body(createScript)
    }

    }
