package com.lodny.springkotlinrealworld.common

import com.lodny.springkotlinrealworld.user.User
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.util.*
import javax.crypto.SecretKey
import io.jsonwebtoken.UnsupportedJwtException as UnsupportedJwtException
import java.lang.SecurityException as SecurityException

@Component
class JwtProvider(@Value("\${jwt.secret}") val jwtSecret: String,
                  @Value("\${jwt.issuer}") val jwtIssuer: String) {

    val key: SecretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun newToken(user: User): String {
        return Jwts.builder()
            .setId(user.username)
            .setSubject(user.email)
            .setIssuer(jwtIssuer)
            .setExpiration(Date(System.currentTimeMillis() + 10 * 24 * 60 * 60 * 1000)) // 10 days
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun verify(token: String): Claims {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        } catch(e: JwtException) {
            println("> JwtInterceptor : verify() : JwtException")
            throw e
        } catch(e: RuntimeException) {
            println("> JwtInterceptor : verify() : RunTimeException")
            throw JwtException("illegal")
        }

        // public class MalformedJwtException extends JwtException {
        // public class ExpiredJwtException extends ClaimJwtException {
        // public abstract class ClaimJwtException extends JwtException {
        // public class UnsupportedJwtException extends JwtException {
        // class IllegalArgumentException extends RuntimeException {
        // public class SecurityException extends RuntimeException {
    }
}