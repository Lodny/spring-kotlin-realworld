package com.lodny.springkotlinrealworld.comment

import com.lodny.springkotlinrealworld.article.Article
import org.springframework.stereotype.Service

@Service
class CommentService(val commentRepository: CommentRepository) {
//    fun findAll(): MutableIterable<Comment> {
//        return commentRepository.findAll()
//    }
    fun findByArticle(article: Article): MutableIterable<Comment> {
        return commentRepository.findByArticle(article)
    }

    fun register(comment: Comment): Comment {
        return commentRepository.save(comment)
    }

    fun update(comment: Comment) : Comment {
        return commentRepository.save(comment)
    }

    fun delete(comment: Comment) {
        commentRepository.delete(comment)
    }

    fun deleteById(id: Long) {
        commentRepository.deleteById(id)
    }
}