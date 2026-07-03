package com.example.koushien_backend.defaultdata
import com.example.koushien_backend.model.Lecture
import com.example.koushien_backend.model.User
import com.example.koushien_backend.repository.LectureRepository
import com.example.koushien_backend.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class Defaultdata(val userRepository: UserRepository,val lectureRepository: LectureRepository) : CommandLineRunner{
    override fun run(vararg args: String) {

        val lecture1 = Lecture(
            lectureName = "Lecture 1",
            lectureVoice = "test",
            materialsUrl = "test",
            createdAt = LocalDateTime.now(),
        )
val testLecture= lectureRepository.save(lecture1)
        val newUser = User(
            uid="dummy",
            name = "testUser1",
            email="sample@jp",
            permission = true,
            lecture =testLecture
        )
        userRepository.save(newUser)

//        val order1 = Order(
//            name = "testOrder1",
//            price = 1000,
//            user =saveUser
//        )
//        val order2 = Order(
//            name = "testOrder2",
//            price = 2000,
//            user = saveUser
//        )
//        orderRepository.save(order1)
//        orderRepository.save(order2)

//        println("初期値用,${orderRepository.findAll()}")
    }
}