package com.lodny.springkotlinrealworld.common

import com.lodny.springkotlinrealworld.user.User
import javax.servlet.ServletRequest


fun mandatoryUser(request: ServletRequest): User {
    val user = request.getAttribute("user")

    return if (user != null) user as User
           else throw InvalidAuthenticationException()

//    return request.getAttribute("user") as User ?: throw InvalidAuthenticationException()
}

fun optionalUser(request: ServletRequest): User? {
    val user = request.getAttribute("user")

    return if (user != null) user as User
           else null
}
