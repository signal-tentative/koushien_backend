package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Lecture
import com.example.koushien_backend.model.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import com.example.koushien_backend.repository.LectureRepository
import com.example.koushien_backend.repository.UserRepository
import com.example.koushien_backend.service.LectureService
import com.example.koushien_backend.service.S3Service
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.apache.coyote.Request
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDateTime
import kotlin.String

//講義関係
data class RequestLecture(val code:String,val title:String,val description: String,val execute:Boolean,val uid:String);

@RestController
class LectureController(
    private val s3Service: S3Service,
    private val lectureRepository: LectureRepository,
    private val lectureService: LectureService,
    val userRepository: UserRepository,
) {
    //レクチャーを登録する
    @PostMapping("/lectures")
    fun createLecture(
        @RequestParam("code") code: String,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("execute") execute: Boolean,
        @RequestParam("startDate") startDate: LocalDateTime,
        @RequestParam("endDate") endDate: LocalDateTime,
        @RequestParam("uid") uid: String
    ): ResponseEntity<Lecture> {
        val saveUser = userRepository.findByUid(uid)
            ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val lecture = Lecture(
            code = code,
            title = title,
            description = description,
            execute = execute,
            startDate = startDate,
            endDate = endDate,
            user = saveUser
        )
        val savedLecture = lectureRepository.save(lecture)

        return ResponseEntity.ok(savedLecture)
    }

    @PatchMapping("/lectures/{code}")
    fun  editLecture(
        @RequestParam("code") code: String,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String,
        @RequestParam("execute") execute: Boolean,
        @RequestParam("startDate") startDate: LocalDateTime,
        @RequestParam("endDate") endDate: LocalDateTime,
        @RequestParam("uid") uid: String): ResponseEntity<Lecture> {

        val patchLecture = lectureRepository.findLectureByCode(code)
        ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val updatedLecture = patchLecture.copy(
            code = code,
            title = title,
            description = description,
            execute = execute,
            startDate = startDate,
            endDate = endDate,
            user = patchLecture.user,
        )

        val savedLecture = lectureRepository.save(updatedLecture)
        return ResponseEntity.ok(savedLecture)
    }

    @GetMapping("/lectures/code/{code}")
    fun getLectureByCode(@PathVariable code:String): Lecture?{
            return lectureRepository.findLectureByCode(code);
        }
    @PutMapping("/lectures/{lecture_id}")
    fun updateLecture(@PathVariable lecture_id: Long,@RequestBody request: RequestLecture): Lecture? {
        return lectureService.updateLecture(lecture_id,request)
    }

    @GetMapping("/lectures")
    fun getLectures(): List<Lecture?>{
        return lectureService.getLectures()
    }
    @GetMapping("/lectures/{lectureId}")
    fun getLecture(@PathVariable lectureId: Long): Lecture? {
        return lectureService.getLectureById(lectureId)
    }
    @GetMapping("/lectures/uid/{uid}")
    fun getLectureByUid(@PathVariable uid: String): List<Lecture?> {
        return  lectureRepository.findLectureByUserUid(uid)
    }

    @DeleteMapping("/lectures/{lectureId}")
    fun deleteLecture(@PathVariable lectureId: Long) {
        return lectureRepository.deleteById(lectureId)
    }
    @DeleteMapping("/lectures/code/{code}")
    fun deleteLecture(@PathVariable code: String) {
        return lectureRepository.deleteLectureByCode(code)
    }
}
