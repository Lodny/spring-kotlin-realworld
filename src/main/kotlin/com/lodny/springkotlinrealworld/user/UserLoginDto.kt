package com.lodny.springkotlinrealworld.user

import com.fasterxml.jackson.annotation.JsonRootName
import javax.validation.constraints.NotBlank

@JsonRootName("user")
class UserLoginDto {

    @NotBlank(message = "can't be blank")
    var email = ""

    @NotBlank(message = "can't be blank")
    var password = ""

    override fun toString() = "$email, $password"
}
