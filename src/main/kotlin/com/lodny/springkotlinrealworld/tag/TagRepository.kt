package com.lodny.springkotlinrealworld.tag

import com.lodny.springkotlinrealworld.article.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface TagRepository : CrudRepository<Tag, Long> {
    fun findByName(tag: String): Tag?

    fun findAll(paging: Pageable): Page<Tag>
}