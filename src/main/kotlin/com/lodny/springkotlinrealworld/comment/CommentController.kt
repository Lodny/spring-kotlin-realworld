package com.lodny.springkotlinrealworld.comment

import com.lodny.springkotlinrealworld.article.ArticleService
import com.lodny.springkotlinrealworld.common.mandatoryUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.ServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = ["http://localhost:3000"])
class CommentController(val commentService: CommentService, val articleService: ArticleService) {

    @GetMapping("/{slug}/comments")
    fun findByArticle(@PathVariable slug: String): ResponseEntity<Any?> {
        println("CommentController.findByArticle : slug = $slug")

        val article = articleService.findBySlug(slug)
        val comments = commentService.findByArticle(article)

        return ResponseEntity(mapOf("comments" to comments), HttpStatus.OK)
    }

    @PostMapping("/{slug}/comments")
    fun register(@Valid @RequestBody commentDto: CommentRegisterDto, @PathVariable slug: String, request: ServletRequest): ResponseEntity<Any?> {
        println("CommentController.register")

        val user = mandatoryUser(request)
        val article = articleService.findBySlug(slug)

        val comment = Comment(commentDto.body, user, article)
        commentService.register(comment)

        return ResponseEntity(mapOf("comment" to comment), HttpStatus.CREATED)
    }

    @DeleteMapping("/{slug}/comments/{id}")
    fun delete(@PathVariable slug: String, @PathVariable id: Long): ResponseEntity<Any?> {
        println("CommentController.delete : slug = $slug, id = $id")

        commentService.deleteById(id)

        return ResponseEntity.ok("")
    }
}