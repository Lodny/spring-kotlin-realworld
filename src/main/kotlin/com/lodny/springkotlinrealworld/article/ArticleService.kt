package com.lodny.springkotlinrealworld.article

import com.lodny.springkotlinrealworld.common.NotFoundException
import com.lodny.springkotlinrealworld.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class ArticleService(val articleRepository: ArticleRepository) {

//    fun findAll(): MutableIterable<Article> {
//        return articleRepository.findAll()
//    }

    fun findAll(limit: Int, offset: Int): List<Article> {
        println("ArticleService.findAll")
        val paging = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "createdAt")
        return articleRepository.findAll(paging).toList()
    }

    fun findByAuthor(author: User, limit: Int, offset: Int): List<Article> {
        val paging = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "createdAt")
        return articleRepository.findByAuthor(author, paging).toList()
    }

    fun getArticlesByTag(tag: String, limit: Int, offset: Int): List<Article> {
        val paging = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "createdAt")
        return articleRepository.findByTagListIn(listOf(tag), paging).toList()
    }

    fun getFeeds(follows: List<User>, limit: Int, offset: Int): List<Article> {
        val paging = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "createdAt")
        return articleRepository.findByAuthorIn(follows, paging).toList()
    }

    fun getFavorites(favorites: List<Article>, limit: Int, offset: Int): List<Article> {
        val paging = PageRequest.of(offset / 10, limit, Sort.Direction.DESC, "createdAt")
        return articleRepository.findByIdIn(favorites.map { it.id }, paging).toList()
    }

    fun register(article: Article): Article {
        return articleRepository.save(article)
    }

    fun findBySlug(slug: String): Article {
        return articleRepository.findBySlug(slug) ?: throw NotFoundException("article", "$slug is not found.")
    }

    fun notFindBySlug(slug: String): Boolean {
        return articleRepository.findBySlug(slug) == null
    }

    fun update(article: Article): Article {
        return articleRepository.save(article)
    }

    fun delete(article: Article) {
        articleRepository.delete(article)
    }

    fun deleteById(id: Long) {
        articleRepository.deleteById(id)
    }

    fun deleteBySlug(slug: String) {
        articleRepository.deleteBySlug(slug)
    }

}
