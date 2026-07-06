package com.example.koushien_backend // 💡ご自身のパッケージ名

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.io.ClassPathResource

@SpringBootApplication
class KoushienBackendApplication

fun main(args: Array<String>) {
	try {
		if (FirebaseApp.getApps().isEmpty()) {
			val jsonName = "koushien-d407a-firebase-adminsdk-fbsvc-661875c004.json"
			val resource = ClassPathResource(jsonName)

			if (!resource.exists()) {
				error("  $jsonName が見つかりません！")
			}

			val options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(resource.inputStream))
				.build()

			FirebaseApp.initializeApp(options)
			println("初期化されました")
		}
	} catch (e: Exception) {
		println("初期化中にエラーが発生しました:")
		e.printStackTrace()
	}
	runApplication<KoushienBackendApplication>(*args)
}
