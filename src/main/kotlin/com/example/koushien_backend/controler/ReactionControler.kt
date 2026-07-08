package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Reaction
import com.example.koushien_backend.repository.LectureRepository
import com.example.koushien_backend.repository.ReactionRepository
import com.example.koushien_backend.repository.UserRepository
import com.example.koushien_backend.service.ReactionService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

//分からないボタン
@RestController
class ReactionControler(val reactionService: ReactionService,val reactionRepository: ReactionRepository ,val userRepository: UserRepository,val lectureRepository: LectureRepository) {
    @GetMapping("/reactions")
    fun getReaction():List<Reaction?>{
        return reactionService.getReaction()
    }
    @PostMapping("/reactions")
    fun saveReaction(
        @RequestParam("lecture_id") lecture_id: Long,
        @RequestParam("user_uid") user_uid: String,
        @RequestParam("time") time: LocalDateTime,
    ): ResponseEntity<Reaction> {
        val savedUser = userRepository.findByUid(user_uid)?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        val savedLecture = lectureRepository.findByIdOrNull(lecture_id)?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val saveReaction = Reaction(
            time = time,
            user = savedUser,
            lecture = savedLecture
        )
        val createReaction = reactionRepository.save(saveReaction)
        return ResponseEntity.ok().body(createReaction)
    }
}