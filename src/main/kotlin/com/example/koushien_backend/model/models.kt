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
    val id: Long = 0L,
    var name: String = "",
    var email: String = "",
    var permission: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    @JoinColumn(name="lecture_id", nullable = false)
    val lecture: Lecture?=null
)
@Entity
@Table(name = "lectures")
data class Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val lecture_name: String = "",
    val Materials: String = "",
    val lecture_voice:String="",
    val createdAt: LocalDateTime = LocalDateTime.now()
)

@Entity
@Table(name="dont_knows")
data class DontKnow(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val dont_know_text:String = "",
    val timestamp: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    val user: User? = null,
    @ManyToOne
    @JoinColumn(name="lecture_id", nullable = false)
    val lecture: Lecture? = null
)
@Entity
@Table(name="q_ta")
data class Q_ta(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val student_id: Long = 0L,
    val lecture_id: Long = 0L,
    val instructor_id: Long = 0L,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
@Entity
@Table(name="transcription")
data class Transcription(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val lecture_id: Long = 0L,
    val transcription: String = "",
    val timestamp: LocalDateTime = LocalDateTime.now(),
)