package com.lodny.springkotlinrealworld.user

import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {

    fun register(user: User) {
        println("UserService.register : $user")

        userRepository.save(user)
    }

    fun findByUsernameOrEmail(user: UserRegisterDto) : User? {
        println("UserService.findByUsernameOrEmail : $user")

        var findUser = userRepository.findByUsername(user.username)
        if (findUser != null) return findUser
        return userRepository.findByEmail(user.email)
    }
}
