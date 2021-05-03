package com.lodny.springkotlinrealworld.comment

import com.fasterxml.jackson.annotation.JsonRootName
import javax.validation.constraints.NotBlank

@JsonRootName("comment")
class CommentRegisterDto {
    @NotBlank(message = "can't be blank")
    val body = ""
}