package com.lodny.springkotlinrealworld.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonRootName
import javax.persistence.*

@Entity
@JsonRootName("user")
data class User(@Column(unique = true, nullable = false) var username: String,
                @Column(unique = true, nullable = false) var email: String,
                @JsonIgnore var password: String) {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    var bio = ""
    var image = ""
    var createdAt = ""
    var updatedAt = ""

    @JsonIgnore
    @OneToMany
    var following = mutableListOf<User>()

//    @JsonIgnore
//    @OneToMany
//    var favorites =  mutableListOf<String>()
//    var favorites: MutableList<String> =  mutableListOf()

    init {
    }

    override fun toString() = "User($username, $email)"
}