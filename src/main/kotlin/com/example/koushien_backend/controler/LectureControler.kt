package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Lecture
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import com.example.koushien_backend.repository.LectureRepository
import com.example.koushien_backend.service.S3Service

//講義関係
data class RequestLecture(val lectureName:String,val lectureVoice:String,val materialsUrl: String);

@RestController
class LectureController(
    private val s3Service: S3Service,
    private val lectureRepository: LectureRepository
) {
    //レクチャーを登録する
    @PostMapping
    fun createLecture(
        @RequestParam("lectureName") lectureName: String,
        @RequestParam("lectureVoice") lectureVoice: String,
        @RequestParam("file") file: MultipartFile // フロントから届くPDFファイル
    ): ResponseEntity<Lecture> {

        // S3にファイルをアップロードし、URLを取得
        val materialsUrl = s3Service.uploadFile(file)

        // DBに保存する形にする
        val lecture = Lecture(
            lectureName = lectureName,
            lectureVoice = lectureVoice,
            materialsUrl = materialsUrl // S3のURL
        )

        // データベースに保存
        val savedLecture = lectureRepository.save(lecture)

        return ResponseEntity.ok(savedLecture)
    }
}
