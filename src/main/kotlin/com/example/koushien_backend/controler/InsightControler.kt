package com.example.koushien_backend.controler
import com.example.koushien_backend.dataclass.chat
import com.example.koushien_backend.model.Insight
import com.example.koushien_backend.model.Transcript
import com.example.koushien_backend.repository.InsightRepository
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import com.example.koushien_backend.repository.LectureRepository
import com.example.koushien_backend.repository.ReactionRepository
import com.example.koushien_backend.repository.StudentRepository
import com.example.koushien_backend.repository.TranscriptRepository
import com.example.koushien_backend.service.InsightService
import com.example.koushien_backend.service.ServiceClass
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.time.LocalDateTime
import kotlin.String
import com.example.koushien_backend.service.AwsS3Service
//import kotlin.io.encoding.Base64
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import java.io.File
import javax.imageio.ImageIO
import org.apache.pdfbox.Loader
import java.util.Base64


@RestController
class  InsightControler(
    val insightService: InsightService,
    val insightRepository: InsightRepository,
    private val lectureRepository: LectureRepository,
    val transcriptRepository: TranscriptRepository,
    val reactionRepository: ReactionRepository,
    val studentRepository: StudentRepository,
    val service: ServiceClass,
    val s3Service: AwsS3Service
){
    @GetMapping("/insights")
    fun getInsight():List<Insight?>{
        return insightService.getInsights()
    }
    @GetMapping("/insights/status/{lecture_id}")
    fun getInsightStatus(@PathVariable("lecture_id") lecture_id: Long,):List<Insight?>{
        return insightService.getInsightById(lecture_id)
    }

    @PostMapping("/insights")
    suspend fun createInsight(
//        @RequestParam("insight_id") insight_id: Long,
        @RequestParam("lecture_id") lecture_id: Long,
        @RequestParam("time") time: LocalDateTime,
        @RequestParam("insight") insight: String,
        @RequestParam("rate") rate: Double,
        @RequestParam("checked") checked: Boolean,
    ):ResponseEntity<Insight> {
        val savedLecture =
            lectureRepository.findByIdOrNull(lecture_id) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
//        val savedInsight =
//            insightRepository.findByIdOrNull(insight_id) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        val savedReaction =
            reactionRepository.findAllById(lecture_id) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        val savedStudent =
            studentRepository.findAllById(lecture_id) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

        val reactionCount = savedReaction.size.toDouble()
        val studentCount = savedStudent.size.toDouble()
        val ratekun: Double = reactionCount / studentCount
//transcriptから今のページのimgを取ってくるアル
        if (ratekun > 30.0) {
            val savedTranscript: List<Transcript?> =
                transcriptRepository.findByTimeTrump(lecture_id, time)
            val imgTranscript =
                transcriptRepository.findByIdOrNull(lecture_id) ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build()
            val key = imgTranscript.document?.link?.substringAfter("com/")
            val img = key?.let { s3Service.downloadImageToLocalFile(it) }
            val pdfFile = imgTranscript.document?.let { File(it.link) }
            val outputFile = File("output.jpg")

            // PDFを読み込む
            Loader.loadPDF(img).use { document ->
                val renderer = PDFRenderer(document)

                // 0ページ目をDPI 300のRGB画像として描画する
                val image = renderer.renderImageWithDPI(imgTranscript.page, 300f, ImageType.RGB)

                // ファイルにJPEGとして出力する
                ImageIO.write(image, "jpg", outputFile)
            }
            val fileBytes = outputFile.readBytes()
            val file64 = Base64.getEncoder().encodeToString(fileBytes)
            println(111111)
            println(savedTranscript)
            val chainText = savedTranscript.map { it?.transcript ?: "textなし" }.joinToString("")
//            val chainText = "どんな画像ですか？"
            println(chainText)
            suspend fun getAnswer(text: String, img: String): String {
                val getAnswer = service.chat(text, img)
                return getAnswer
            }

            val answer=  getAnswer(chainText, file64)
            println(answer)
            val newInsight = Insight(
                time = time,
                insight = answer,
                rate = ratekun,
                checked = true,
                lecture = savedLecture
            )
            val updatedInsight = insightRepository.save(newInsight)
            return ResponseEntity.ok().body(updatedInsight)
        }else{
            val newInsight = Insight(
                time = time,
                insight = "",
                rate = ratekun,
                checked = false,
                lecture = savedLecture
            )
            val updatedInsight = insightRepository.save(newInsight)
            return ResponseEntity.ok().body(updatedInsight)
        }
    }

//    @PutMapping("/insights")
//    fun updateInsight(
//        @RequestParam("insight_id") insight_id: Long,
//        @RequestParam("lecture_id") lecture_id: Long,
//        @RequestParam("time") time: LocalDateTime,
//        @RequestParam("insight") insight: String,
//        @RequestParam("rate") rate: Double,
//        @RequestParam("check") check: Boolean,
//    ):ResponseEntity<Insight>{
//        val savedLecture = lectureRepository.findByIdOrNull(lecture_id)?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
//        val savedInsight = insightRepository.findByIdOrNull(insight_id) ?:return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
//        val savedReaction = reactionRepository.findAllById(lecture_id)?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
//        val savedStudent = studentRepository.findAllById(lecture_id)?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
//
//        if(rate >30.0){
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
//        }
//
//        val copiedInsight = savedInsight.copy(
//            time = time,
//            insight = insight,
//            rate = rate,
//            check = check,
//            lecture = savedLecture
//        )
//        val updatedInsight = insightRepository.save(copiedInsight)
//        return ResponseEntity.ok().body(updatedInsight)
//    }
}

