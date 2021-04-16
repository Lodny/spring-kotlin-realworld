package com.lodny.springkotlinrealworld.user

import org.hibernate.exception.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
//@CrossOrigin("localhost:3000")
class UserController(val userService: UserService) {

//    @GetMapping("/users")
//    fun getAllUser() : ResponseEntity<String> {
//        println("> users[GET] : getAllUser() :")
//        return ResponseEntity("ok", HttpStatus.OK)
//    }

    @PostMapping("/users")
    fun register(@Valid @RequestBody user: UserRegisterDto, result: BindingResult) : ResponseEntity<Any> {
        println("> users[POST] : register() : $user")

//        if (userService.findByUsernameOrEmail(user) != null)
//            return ResponseEntity("duplicate username or email", HttpStatus.UNPROCESSABLE_ENTITY)
        val user = User(user.username, user.email, user.password)
        try {
            userService.register(user)
        } catch (e: DataIntegrityViolationException) {
            println("> Duplicated Error : Exception : $e")
        }

        return ResponseEntity(mapOf("user" to user), HttpStatus.CREATED)
    }

    @PostMapping("/users/login")
    fun login(@Valid @RequestBody user: UserLoginDto, result: BindingResult) : ResponseEntity<Any> {
        println("> users[POST] : login() : $user")

        return ResponseEntity("ok", HttpStatus.OK)
    }

    @PutMapping("/user")
    fun update(@Valid @RequestBody user: UserUpdateDto, result: BindingResult) : ResponseEntity<Any> {

        println("> users[PUT] : update() : $user")
        return ResponseEntity("ok", HttpStatus.OK)
    }
}