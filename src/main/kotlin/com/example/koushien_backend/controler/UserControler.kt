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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.koushien_backend.repository.UserRepository
data class RequestUser(val uid: String,val name:String, val email:String, val permission:Boolean);

@RestController
class UserController(
    val userService: UserService,
    val userRepository: UserRepository
){

    @PostMapping("/login")
    fun login(@RequestHeader("Authorization") authorizationHeader: String, @RequestBody request: RequestUser,): ResponseEntity<Map<String, Any>> {
        // トークン文字列を抽出
        if (!authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "無効なトークン形式です"))
        }
        println(request)
        val token = request.uid
        return try {

            val uid = token
            val email = request.email
            val name =request.name
            val permission = request.permission

            ResponseEntity.ok(mapOf(
                "message" to "認証成功",
                "uid" to uid,
                "email" to email,
                "name" to name,
                "permission" to permission,
            ))
        } catch (e: FirebaseAuthException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "トークンの検証に失敗しました: ${e.message}"))
        }
    }
    @PostMapping("/signup")
    fun registerUser(
        @RequestHeader("Authorization") authorizationHeader: String,
        @RequestBody request: RequestUser,
    ): ResponseEntity<Any> {

        //  トークンの取り出し
        val token = if (authorizationHeader.startsWith("Bearer ")) {
            authorizationHeader.substring(7)
        } else {
            authorizationHeader
        }

        return try {
            val uid = request.uid
            println(uid)
            if (userRepository.existsByUid(uid)) {
                return ResponseEntity.ok(mapOf("message" to "このユーザーは既にDBに登録されています"))
            }

            val newUser = User(
                uid = uid,
                email = request.email,
                name = request.name ?: ""
            )
            userRepository.save(newUser)

            ResponseEntity.ok(mapOf("message" to "DBへのユーザー登録が成功しました", "uid" to uid))

        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("error" to "認証に失敗したため登録できません: ${e.message}"))
        }
    }

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