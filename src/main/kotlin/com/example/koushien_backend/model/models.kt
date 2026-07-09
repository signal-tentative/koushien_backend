package com.example.koushien_backend.model
import com.google.type.DateTime
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import jakarta.persistence.*
import java.io.File
import kotlin.time.*
import kotlin.time.Duration.Companion.milliseconds
@Entity
@Table(name="users")
data class User(//講師のテーブル
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var uid: String = "", //firebaseのID
    var name: String = "",//ユーザー名
)

@Entity
@Table(name = "lectures")
data class Lecture(//講義テーブル
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val code: String = "",//講義コード
    var title: String = "",//講義名
    var startDate: LocalDateTime ,//講義開始時間
    var endDate: LocalDateTime ,//講義終了時間
    var description: String = "",//講義説明
    var execute:Boolean = false,//実施有無
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,//userのid この講義の講師
)

@Entity
@Table(name = "documents")
data class Document(//資料データ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var link: String = "",//資料リンク（S3）
    var page: Int = 0,//投入された資料のページ数
    var thumnailPath: String = "",
    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    var lecture: Lecture? = null,//lectureのid
)

@Entity
@Table(name = "students")
data class Student(//受講生データ
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    var lecture: Lecture? = null, // lecture 1 対　多　student
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,  //user 1　対　多　student
)

@Entity
@Table(name = "transcripts")
data class Transcript(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var time: LocalDateTime = LocalDateTime.now(),//文字おこし開始時間
    var transcript: String = "",
    var page: Int = 0,//その時の資料ページ
    @ManyToOne
    @JoinColumn(name ="lecture_id", nullable = false)
    var lecture: Lecture? = null,// lecture 1 対　多 transcript
    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    var document: Document? = null,
)

@Entity
@Table(name="reactions")
data class Reaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var time: LocalDateTime ,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,
    @ManyToOne
    @JoinColumn(name="lecture_id", nullable = false)
    var lecture: Lecture? = null
)

@Entity
@Table(name = "insights")
data class Insight(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val time: LocalDateTime,//経過時間管理
    var rate: Double = 0.0, //分からない率
    @Column(name = "checked", nullable = true)
    var checked:Boolean = false,//rateをチェックする
    @Column(columnDefinition = "TEXT")
    var insight : String = "",//transcriptをAIに渡した返り値を保存
    @ManyToOne
    @JoinColumn(name = "lecture_id", nullable = false)
    var lecture: Lecture? = null,//監視中のレクチャー
)

@Entity
@Table(name ="script")
data class Script(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var page: Int = 0,
    var script: String = "",
    @ManyToOne
    @JoinColumn(name="document_id", nullable =false)
    var document: Document? = null,
)