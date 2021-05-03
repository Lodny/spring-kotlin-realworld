package com.lodny.springkotlinrealworld.article

import com.fasterxml.jackson.annotation.JsonRootName
import com.github.slugify.Slugify
import com.lodny.springkotlinrealworld.user.User
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
data class Article(@Column(nullable = false) var title: String = "",
                   @Column(nullable = false) var description:String = "",
                   @Column(nullable = false) var body: String = "",
                   @ElementCollection(fetch = FetchType.EAGER) val tagList: MutableList<String> = mutableListOf(),
                   @ManyToOne var author: User = User(),
                   @Column(unique = true, nullable = false) var slug: String = "",
                   @CreationTimestamp var createdAt: LocalDateTime = LocalDateTime.now(),
                   @CreationTimestamp var updatedAt: LocalDateTime = LocalDateTime.now(),
                   var favoritesCount: Int = 0,
                   @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0) {

    constructor(dto: ArticleRegisterDto, author: User, slug: String): this(dto.title, dto.description, dto.body, dto.tagList, author, slug)

    override fun toString(): String = "Article($slug, $id)"

    override fun equals(other: Any?): Boolean {
        val article = other as Article
        return id == article.id && slug == article.slug && author.username == article.author.username
    }

    fun update(articleDto: ArticleRegisterDto, slug: String) {
        title = articleDto.title
        description = articleDto.description
        body = articleDto.body
        title = articleDto.title
        this.slug = slug
        updatedAt = LocalDateTime.now()
    }

    inner class Article2Json(loginUser: User?) {
        val title = this@Article.title
        val description = this@Article.description
        val body = this@Article.body
        val tagList = this@Article.tagList
        val author =  this@Article.author.User2Json(loginUser)
        val slug = this@Article.slug
        val createdAt = this@Article.createdAt
        val updatedAt = this@Article.updatedAt
        val favoritesCount = this@Article.favoritesCount
        val favorited = loginUser?.favorites?.contains(this@Article) ?: false
    }
}