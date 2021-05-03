package com.lodny.springkotlinrealworld.profile

import com.fasterxml.jackson.annotation.JsonRootName
import com.lodny.springkotlinrealworld.user.User

@JsonRootName("profile")
data class Profile(var username: String,
                   var bio: String,
                   var image: String?,
                   var following: Boolean) {
//    companion object {
//        fun fromUser(user: User, loginUser: User): Profile {
//            return Profile(username = user.username,
//                           bio = user.bio,
//                           image = user.image,
//                           following = loginUser.follows.contains(user))
//        }
//    }
}