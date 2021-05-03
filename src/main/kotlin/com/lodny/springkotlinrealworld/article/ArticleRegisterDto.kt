package com.lodny.springkotlinrealworld.article

import com.fasterxml.jackson.annotation.JsonRootName
import javax.validation.constraints.NotBlank

@JsonRootName("article")
class ArticleRegisterDto {
    @NotBlank(message = "can't be blank")
    var title = ""

    @NotBlank(message = "can't be blank")
    var description = ""

    @NotBlank(message = "can't be blank")
    var body = ""

    var tagList = mutableListOf<String>()
}