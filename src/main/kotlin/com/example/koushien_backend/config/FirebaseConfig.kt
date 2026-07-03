//package com.example.koushien_backend
//
//import com.google.auth.oauth2.GoogleCredentials
//import com.google.firebase.FirebaseApp
//import com.google.firebase.FirebaseOptions
//import org.slf4j.LoggerFactory
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.io.ResourceLoader
//import javax.annotation.PostConstruct
//
//@Configuration
//class FirebaseConfig(private val resourceLoader: ResourceLoader) {
//    private val logger = LoggerFactory.getLogger(FirebaseConfig::class.java)
//
//    @Value("\${firebase.config-path}")
//private lateinit var configPath: String
//
//   @PostConstruct
//   fun initialize() {
//        try {
//            logger.info("Firebase初期化を開始します。設定ファイル: $configPath")
//
//            if (FirebaseApp.getApps().isEmpty()) {
//            val resource = resourceLoader.getResource(configPath)
//            if (!resource.exists()) {
//            throw IllegalArgumentException("Firebaseの設定ファイルが見つかりません: $configPath")
//            }
//
//            logger.info("Firebaseの設定ファイルを読み込んでいます: ${resource.filename}")
//
//            val options = FirebaseOptions.builder()
//            .setCredentials(GoogleCredentials.fromStream(resource.inputStream))
//            .build()
//
//            FirebaseApp.initializeApp(options)
//            logger.info(":sparkles: Firebase Admin SDK の初期化に成功しました！")
//            } else {
//            logger.info("Firebase は既に初期化されています")
//            }
//            } catch (e: Exception) {
//            logger.error(":x: Firebaseの初期化中にエラーが発生しました:", e)
//            throw RuntimeException("Firebase初期化エラー: ${e.message}", e)
//            }
//   }
//}