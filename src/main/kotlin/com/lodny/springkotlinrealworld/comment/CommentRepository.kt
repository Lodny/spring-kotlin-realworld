package com.lodny.springkotlinrealworld.comment

import com.lodny.springkotlinrealworld.article.Article
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : CrudRepository<Comment, Long> {
    fun findByArticle(article: Article): MutableIterable<Comment>

}