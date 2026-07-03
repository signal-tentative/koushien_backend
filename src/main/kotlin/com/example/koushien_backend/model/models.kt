package com.example.koushien_backend.model
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import jakarta.persistence.*
import java.io.File

@Entity
@Table(name="users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var name: String = "",
    var email: String = "",
    var permission: Boolean = false,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    @JoinColumn(name="lecture_id", nullable = false)
    var lecture: Lecture?=null
)
@Entity
@Table(name = "lectures")
data class Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var lectureName: String = "",
    var materialsUrl: String = "",
    var lectureVoice:String="",
    var createdAt: LocalDateTime = LocalDateTime.now()
)

@Entity
@Table(name="dont_knows")
data class DontKnow(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var dontKnowText:String = "",
    var timestamp: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    var user: User? = null,
    @ManyToOne
    @JoinColumn(name="lecture_id", nullable = false)
    var lecture: Lecture? = null
)
@Entity
@Table(name="q_ta")
data class Qta(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var studentId: Long = 0L,
    var lectureId: Long = 0L,
    var instructorId: Long = 0L,
    var timestamp: LocalDateTime = LocalDateTime.now(),
)
@Entity
@Table(name="transcription")
data class Transcription(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var lectureId: Long = 0L,
    var transcription: String = "",
    var timestamp: LocalDateTime = LocalDateTime.now(),
)