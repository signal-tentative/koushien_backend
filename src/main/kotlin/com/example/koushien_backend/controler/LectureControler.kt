package com.example.koushien_backend.controler
import com.example.koushien_backend.model.Lecture
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import com.example.koushien_backend.model.User
import com.example.koushien_backend.service.LectureService
data class RequestLecture(val lecture_name:String,val Lecture_voice:String,val Materials: String);

@RestController
class LectureController(
    val lectureService: LectureService,
){
    @GetMapping("/lectures")
    fun getUsers():List<Lecture?> {
        return lectureService.getLecture()
    }
    //    @PutMapping("/users/{id}")
//    fun updateUser(@PathVariable id:Long,@RequestBody request: RequestUser):User{
//        return userService.updateUser(id,request)
//    }
//    @PostMapping("/users")
//    fun createUser(@RequestBody request:RequestUser):User{
//        return userService.createUser(request);
//    }
//    @GetMapping("/users/{id}")
//    fun getUserById(@PathVariable id:Long):User{
//        return userService.getUser(id)
//    }
//    @DeleteMapping("/users/{id}")
//    fun deleteUserById(@PathVariable id:Long):User{
//        return userService.deleteUser(id)
//    }
}