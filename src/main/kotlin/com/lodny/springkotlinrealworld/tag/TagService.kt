package com.lodny.springkotlinrealworld.tag

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class TagService(val tagRepository: TagRepository) {
    fun update(tagList: MutableList<String>) {
        println("TagService.update")

        for (tagStr in tagList) {
            println("tag = ${tagStr}")

            val tag = tagRepository.findByName(tagStr) ?: Tag(tagStr)
            tag.count++
            tagRepository.save(tag)
        }
    }

    fun getTags(): List<String> {
        println("TagService.getTags")

        val paging = PageRequest.of(0, 3, Sort.Direction.DESC, "count")
        return tagRepository
            .findAll(paging)
            .map { it.name }
            .toList()
    }
}