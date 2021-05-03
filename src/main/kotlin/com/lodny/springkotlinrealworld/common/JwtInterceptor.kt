package com.lodny.springkotlinrealworld.common

import com.lodny.springkotlinrealworld.user.UserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtInterceptor(val jwt: JwtProvider, val userService: UserService): HandlerInterceptor {

    private val auth = "Authorization"

    @Transactional
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = getTokenString(request.getHeader(auth)) ?: return true

        println("> JwtInterceptor : preHandle() : token : $token")
        val username = jwt.verify(token).id
        userService.findByUsername(username)?.let {
            println("> JwtInterceptor : preHandle() : username = $it, follow.size = ${it.follows.size}, favorites.size = ${it.favorites.size}")
            request.setAttribute("user", it)
        }
//
//
//        getTokenString(request.getHeader(auth))?.let {
//
//            println("> JwtInterceptor : preHandle() : token : $it");
//            jwt.verify(it).id?.let {
//                userService.findByUsername(it)?.let {
//                    request.setAttribute("user", it)
//                }
//            }
//        }

        //SignatureException

        return true
    }

    private fun getTokenString(header: String?): String? {
        if (header == null) return null

        return header.split(" ").let {
            if (it.size < 2) null else it[1]
        }
    }
}