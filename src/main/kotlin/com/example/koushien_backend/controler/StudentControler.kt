package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Lecture
import com.example.koushien_backend.model.Student
import com.example.koushien_backend.model.User
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import com.example.koushien_backend.repository.LectureRepository
import com.example.koushien_backend.repository.StudentRepository
import com.example.koushien_backend.repository.UserRepository
import com.example.koushien_backend.service.LectureService
import com.example.koushien_backend.service.S3Service
import com.example.koushien_backend.service.StudentService
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.apache.coyote.Request
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDateTime
import kotlin.String
@RestController
class StudentControler(
    val studentService: StudentService,
    val userRepository: UserRepository,
    private val lectureService: LectureService,
    val studentRepository: StudentRepository,
) {
    @GetMapping("/students")
    fun getStudent():List<Student?>{
        return studentService.getStudents()
    }
    @GetMapping("/students/uid/{uid}")
    fun getStudentLectureByUid(@PathVariable("uid") uid:String):List<Student?>{
        return studentRepository.findByUserUid(uid)
    }
    @PostMapping("/students")
    fun addLecture(
        @RequestParam user_uid:String,
        @RequestParam lecture_id:Long
    ):ResponseEntity<Student>{
        val saveUser = userRepository.findByUid(user_uid)
            ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val saveLecture = lectureService.getLectureById(lecture_id)
        ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val student = Student(
            lecture=saveLecture,
            user = saveUser,
        )
        val saveStudent = studentRepository.save(student)
        return ResponseEntity.ok().body(saveStudent)
    }
}
