package com.example.koushien_backend.service
import com.example.koushien_backend.controler.RequestLecture
import  com.example.koushien_backend.model.Lecture
import com.example.koushien_backend.repository.LectureRepository
import org.apache.coyote.Request
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SqlLectureService ( val lectureRepository: LectureRepository): LectureService {
    override  fun getLectures(): List<Lecture?> {
        return lectureRepository.findAll()
    }
    override  fun getLectureById(lectureId: Long): Lecture? {
        val findLecture: Lecture? = lectureRepository.findByIdOrNull(lectureId)
        return findLecture
    }

    override  fun updateLecture(lectureId: Long,request: RequestLecture): Lecture? {
        var findLecture: Lecture? = lectureRepository.findByIdOrNull(lectureId)?: throw IllegalArgumentException("User with id ${lectureId} does not exist")
        var newLecture: Lecture? = findLecture?.copy(
           lectureName= request.lectureName,
           lectureVoice=request.lectureVoice,
           beforeAfter = request.beforeAfter,
       )
        return lectureRepository.save(newLecture)
    }
    }
//    override  fun getAll(): List<Order> {
//        return orderRepository.findAll()
//    }
//    override  fun createdOrder(request: RequestOrder): Order{
//        val newOrder = Order(name =request.name, price = request.price)
//        return orderRepository.save(newOrder)
//    }
//    override  fun deleteOrder(id: Long): Order{
//        val deleteOrder = orderRepository.findById(id).orElseThrow { IllegalArgumentException("Order with id $id does not exist") }
//        orderRepository.deleteById(deleteOrder.id)
//        return deleteOrder
//    }
//    override  fun updateOrder(id: Long, request: RequestOrder): Order {
//        if(!orderRepository.existsById(id)){
//            throw IllegalArgumentException("Order with id $id does not exist")
//        }
//        val newOrder = Order(id=id,name=request.name, price = request.price)
//        return orderRepository.save(newOrder)
//    }
