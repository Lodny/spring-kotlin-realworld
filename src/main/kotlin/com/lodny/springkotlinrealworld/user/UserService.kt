package com.lodny.springkotlinrealworld.user

import com.lodny.springkotlinrealworld.common.JwtProvider
import com.lodny.springkotlinrealworld.common.NotFoundException
import com.lodny.springkotlinrealworld.common.UserExistException
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val jwt: JwtProvider) {

    fun register(user: User): User {
        println("> UserService : register() : $user")

        if (userRepository.existsByUsername(user.username))
            throw UserExistException("username", "already taken")

        if (userRepository.existsByEmail(user.email))
            throw UserExistException("email", "already taken")

        return userRepository.save(user)
    }

    fun login(userDto: UserLoginDto): User {
        return userRepository.findByEmail(userDto.email) ?: throw NotFoundException(
            "email or password",
            "is invalid."
        )
    }

    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    fun update(user: User, userDto: UserUpdateDto): User {
        if (user.username != userDto.username) {
            if (userRepository.existsByUsername(userDto.username))
                throw UserExistException("username", "already taken")

            user.username = userDto.username
        }

        if (user.email != userDto.email) {
            if (userRepository.existsByEmail(user.email))
                throw UserExistException("email", "already taken")

            user.email = userDto.email
        }

        user.bio = userDto.bio
        user.image = userDto.image
        user.password = userDto.password

        return userRepository.save(user)
    }

    fun update(user: User): User {
        return userRepository.save(user)
    }

//    fun follow(user: User, loginUser: User) {
//        user.following.add(loginUser)
//        userRepository.save(user)
//    }
}
