package com.lodny.springkotlinrealworld.user

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean

    fun findByUsername(username: String) : User?
    fun findByEmail(email: String) : User?
}
