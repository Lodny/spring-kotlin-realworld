package com.lodny.springkotlinrealworld.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonRootName
import com.lodny.springkotlinrealworld.article.Article
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@JsonRootName("user")
data class User(@Column(unique = true, nullable = false) var username: String = "",
                @Column(unique = true, nullable = false) var email: String = "",
                @JsonIgnore @NotEmpty var password: String = "",
                @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0,
                var bio: String = "",
                var image: String = "https://static.productionready.io/images/smiley-cyrus.jpg",
//                var createdAt: Date = Date(),
//                var updatedAt: Date = Date(),
                @ManyToMany
                @JsonIgnore
                var follows: MutableList<User> = mutableListOf(),
//                @OneToMany(fetch = FetchType.LAZY)
                @OneToMany
                @JsonIgnore
                var favorites: MutableList<Article> =  mutableListOf(),
                var token: String = ""
                ) {
    // *** The toString function below have to be, because data class and following recursive
    override fun toString(): String = "User($email, $username, $id)"

    override fun equals(other: Any?): Boolean {
        val user = other as User
        return id == user.id && username == user.username && email == user.email
    }

    inner class User2Json(loginUser: User?) {
        val username = this@User.username
        val bio = this@User.bio
        val image = this@User.image
        val following = loginUser?.follows?.contains(this@User) ?: false
    }
}