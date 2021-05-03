package com.lodny.springkotlinrealworld.profile

import com.lodny.springkotlinrealworld.common.NotFoundException
import com.lodny.springkotlinrealworld.common.mandatoryUser
import com.lodny.springkotlinrealworld.common.optionalUser
import com.lodny.springkotlinrealworld.user.User
import com.lodny.springkotlinrealworld.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.ServletRequest

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/api/profiles")
class ProfileController(val userService: UserService) {

    @GetMapping("/{username}")
    fun getProfile(@PathVariable username: String, request: ServletRequest): ResponseEntity<Any?> {
        println("ProfileController.getProfile")

        val loginUser: User? = optionalUser(request)
        val user: User = userService.findByUsername(username) ?: throw NotFoundException("profile", "not found")

//        val profile = if (loginUser != null) Profile(user, user.following.indexOf(loginUser) >= 0)
//                      else Profile(user, false)
//        return ResponseEntity(mapOf("profile" to profile), HttpStatus.OK)

        return ResponseEntity(mapOf("profile" to user.User2Json(loginUser)), HttpStatus.OK)
//        return ResponseEntity("OK", HttpStatus.OK)
    }

    @PostMapping("/{username}/follow")
    fun follow(@PathVariable username: String, request: ServletRequest): ResponseEntity<Any?> {
        println("ProfileController.follow : username = $username")

        var loginUser = mandatoryUser(request)
        val user = userService.findByUsername(username) ?: throw NotFoundException("profile", "not found")

        if (loginUser.follows.contains(user))
            loginUser.follows.remove(user)
        else
            loginUser.follows.add(user)

        loginUser = userService.update(loginUser)
//        println("loginUser.follows.size = ${loginUser.follows.size}")

        return ResponseEntity(mapOf("profile" to user.User2Json(loginUser)), HttpStatus.OK)
    }
}