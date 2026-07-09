package com.example.koushien_backend.service
import software.amazon.awssdk.services.s3.model.ObjectCannedACL
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.core.sync.ResponseTransformer
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File
import java.nio.file.Files
import java.util.*

//アップロード処理本体(仮)
@Service
class S3Service(private val s3Client: S3Client) {

    @Value("\${aws.s3.bucket}")
    private lateinit var bucketName: String

    @Value("\${aws.s3.region}")
    private lateinit var region: String

    fun uploadFile(file: MultipartFile): String {
        val originalName = file.originalFilename ?: "file.pdf"
        val extension = originalName.substring(originalName.lastIndexOf("."))
        val fileKey = "materials/${UUID.randomUUID()}$extension"

        // ファイル名は取り出す時にやりやすいやつに

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileKey)
            .contentType(file.contentType)
            .acl(ObjectCannedACL.PUBLIC_READ)
            .build()

        // アップロード
        s3Client.putObject(
            putObjectRequest,
            RequestBody.fromInputStream(file.inputStream, file.size)
        )
        // 公開URL
        return "https://$bucketName.s3.us-east-1.amazonaws.com/$fileKey"
    }
}
@Service
class AwsS3Service(private val s3Client: S3Client) {
    @Value("\${aws.s3.bucket}")
    private lateinit var bucketName: String

    @Value("\${aws.s3.region}")
    private lateinit var region: String

    fun downloadImageToLocalFile(key: String): File {
        // 1. 重複しない安全な一時ファイルのパスを生成（この時点で空ファイルが作られる）
        val tempPath = Files.createTempFile("s3-image-", ".tmp")

        // 2. AWS SDKが新しくファイルを作れるように、一度空ファイルを削除する
        Files.deleteIfExists(tempPath)

        val request = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()

        // 3. ファイルへのダウンロード実行（SDKが安全にファイルを新規作成します）
        s3Client.getObject(request, ResponseTransformer.toFile(tempPath))

        // 4. Fileオブジェクトとして返却
        return tempPath.toFile()
    }

}
