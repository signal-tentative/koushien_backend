package com.example.koushien_backend.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.*

//アップロード処理本体(仮)
@Service
class S3Service(private val s3Client: S3Client) {

    @Value("\${aws.s3.bucket}")
    private lateinit var bucketName: String

    @Value("\${aws.s3.region}")
    private lateinit var region: String

    fun uploadFile(file: MultipartFile): String {
        // ファイル名は取り出す時にやりやすいやつに
        val fileKey = "materials/${file.originalFilename}"

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileKey)
            .contentType(file.contentType)
            .build()

        // アップロード
        s3Client.putObject(
            putObjectRequest,
            RequestBody.fromInputStream(file.inputStream, file.size)
        )

        // 公開URL
        return "https://$bucketName.s3.$://amazonaws.com"
    }
}
