package com.example.koushien_backend.service
import  com.example.koushien_backend.model.Lecture
import com.example.koushien_backend.repository.LectureRepository
import org.springframework.stereotype.Service

@Service
class SqlLectureService ( val lectureRepository: LectureRepository): LectureService {
    override  fun getLecture(): List<Lecture?> {
        return lectureRepository.findAll()
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
