package com.lodny.springkotlinrealworld.tag

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class TagController(val tagService: TagService) {

    @GetMapping("/api/tags")
    fun getTags(): ResponseEntity<Any?> {
        println("TagController.getTags")

        return ResponseEntity(mapOf("tags" to tagService.getTags()), HttpStatus.CREATED)
    }
}