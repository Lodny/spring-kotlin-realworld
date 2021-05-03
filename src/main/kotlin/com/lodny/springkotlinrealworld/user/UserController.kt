package com.lodny.springkotlinrealworld.user

import com.lodny.springkotlinrealworld.common.InvalidAuthenticationException
import com.lodny.springkotlinrealworld.common.JwtProvider
import com.lodny.springkotlinrealworld.common.mandatoryUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.ServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = ["http://localhost:3000"])
class UserController(val userService: UserService, val jwt: JwtProvider) {

//    @GetMapping("/users")
//    fun getAllUser() : ResponseEntity<String> {
//        println("> users[GET] : getAllUser() :")
//        return ResponseEntity("ok", HttpStatus.OK)
//    }

    // must remove BindingResult or Errors for using @RestControllerAdvice(:MethodArgumentNotValidException)
    @PostMapping("/users")
//    fun register(@Valid @RequestBody user: UserRegisterDto, result: BindingResult, errors: Errors) : ResponseEntity<Any> {
    fun register(@Valid @RequestBody userDto: UserRegisterDto) : ResponseEntity<Any> {
        println("> users[POST] : register() : $userDto")

//        val user = userService.register(User(userDto.username, userDto.email, userDto.password))
        val user = userService.register(User(username = userDto.username, email = userDto.email, password = userDto.password))
        user.token = jwt.newToken(user)
//        println(jwt.verify(user.token).id)

        return ResponseEntity(mapOf("user" to user), HttpStatus.CREATED)
    }

    @PostMapping("/users/login")
//    fun login(@Valid @RequestBody user: UserLoginDto, result: BindingResult) : ResponseEntity<Any> {
    fun login(@Valid @RequestBody userDto: UserLoginDto) : ResponseEntity<Any> {
        println("> users[POST] : login() : $userDto")

        val user = userService.login(userDto)
        user.token = jwt.newToken(user)

        return ResponseEntity(mapOf("user" to user), HttpStatus.OK)
    }

    // update token only if email changed
    @PutMapping("/user")
    fun update(@Valid @RequestBody userDto: UserUpdateDto, request: ServletRequest) : ResponseEntity<Any> {
        println("> users[PUT] : update() : $userDto")
        println("> users[PUT] : update() : $request")

        var user = mandatoryUser(request)
        user = userService.update(user, userDto)
        user.token = jwt.newToken(user)

        return ResponseEntity(mapOf("user" to user), HttpStatus.OK)
    }
}