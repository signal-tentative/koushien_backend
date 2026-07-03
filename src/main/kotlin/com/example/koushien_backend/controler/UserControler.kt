package com.example.koushien_backend.controler
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import com.example.koushien_backend.model.User
import com.example.koushien_backend.service.UserService
data class RequestUser(val name:String,val email:String,val permission:Boolean);

@RestController
class UserController(
    val userService: UserService,
){
    @GetMapping("/users")
    fun getUsers():List<User>{
        return userService.getUsers();
    }

    @PostMapping("/users")
    fun createUser(@RequestBody request:RequestUser):User{
        return userService.createUser(request);
    }
    @PutMapping("/users/{id}")
    fun updateUser(@PathVariable id:Long,@RequestBody request: RequestUser):User{
        return userService.updateUser(id,request)
    }

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id:Long):User{
        return userService.getUser(id)
    }
    @DeleteMapping("/users/{id}")
    fun deleteUserById(@PathVariable id:Long):User{
        return userService.deleteUser(id)
    }
}