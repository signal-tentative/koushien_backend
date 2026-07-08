package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Insight
import com.example.koushien_backend.repository.InsightRepository
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import com.example.koushien_backend.repository.LectureRepository
import com.example.koushien_backend.service.InsightService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import java.time.LocalDateTime
import kotlin.String

@RestController
class InsightControler(
    val insightService: InsightService,
    val insightRepository: InsightRepository,
    private val lectureRepository: LectureRepository
){
    @GetMapping("/insights")
    fun getInsight():List<Insight?>{
        return insightService.getInsights()
    }
    @GetMapping("/insights/status/{lecture_id}")
    fun getInsightStatus(@PathVariable("lecture_id") lecture_id: Long,):List<Insight?>{
        return insightService.getInsightById(lecture_id)
    }
    @PutMapping("/insights")
    fun updateInsight(
        @RequestParam("insight_id") insight_id: Long,
        @RequestParam("lecture_id") lecture_id: Long,
        @RequestParam("time") time: LocalDateTime,
        @RequestParam("insight") insight: String,
        @RequestParam("rate") rate: Double,
        @RequestParam("check") check: Boolean,
    ):ResponseEntity<Insight>{
        val savedLecture = lectureRepository.findByIdOrNull(lecture_id)?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        val savedInsight = insightRepository.findByIdOrNull(insight_id) ?:return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        val copiedInsight = savedInsight.copy(
            time = time,
            insight = insight,
            rate = rate,
            check = check,
            lecture = savedLecture
        )
        val updatedInsight = insightRepository.save(copiedInsight)
        return ResponseEntity.ok().body(updatedInsight)
    }

}