package com.lodny.springkotlinrealworld.comment

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonRootName
import com.lodny.springkotlinrealworld.article.Article
import com.lodny.springkotlinrealworld.user.User
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@JsonRootName("comment")
data class Comment(@Column(nullable = false) var body: String,
                   @ManyToOne val author: User,
                   @ManyToOne val article: Article,
                   @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
                   @CreationTimestamp val createdAt: LocalDateTime = LocalDateTime.now(),
                   @CreationTimestamp var updatedAt: LocalDateTime = LocalDateTime.now())
