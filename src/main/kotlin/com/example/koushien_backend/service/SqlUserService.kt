package com.example.koushien_backend.service
import com.example.koushien_backend.controler.RequestUser
import com.example.koushien_backend.repository.UserRepository
import org.springframework.stereotype.Service
import com.example.koushien_backend.model.User
import org.springframework.data.repository.findByIdOrNull

@Service
class SqlUserService(val userRepository: UserRepository) : UserService {
    override fun getUsers():List<User>{
        return userRepository.findAll()
    }

    override fun getUser(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("User with id $id does not exist")
    }

    override fun updateUser(id: Long,request: RequestUser): User {
        val updatedUser = userRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("User with id $id does not exist")
        val newUser = updatedUser.copy(
            name=request.name,
            email=request.email
        )
        return userRepository.save(newUser)
    }
    override fun deleteUser(id: Long) : User {
        val deleteUser = userRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("User with id $id does not exist")
        userRepository.deleteById(id)
        return deleteUser
    }

    override fun createUser(request: RequestUser): User {
        val newUser = User(name = request.name,email=request.email,permission=request.permission)
        return userRepository.save(newUser)
    }
}