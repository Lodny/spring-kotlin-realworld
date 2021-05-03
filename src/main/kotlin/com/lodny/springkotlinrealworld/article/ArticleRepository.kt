package com.lodny.springkotlinrealworld.article

import com.lodny.springkotlinrealworld.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository: CrudRepository<Article, Long> {
    fun findBySlug(slug: String): Article?

    fun findAll(paging: Pageable): Page<Article>
    fun findByAuthor(author: User, paging: Pageable): Page<Article>

    fun deleteBySlug(slug: String)

    fun findByIdIn(ids: List<Long>, paging: Pageable): Page<Article>
    fun findByAuthorIn(follows: List<User>, paging: Pageable): Page<Article>
    fun findByTagListIn(tags: List<String>, paging: Pageable): Page<Article>
}
