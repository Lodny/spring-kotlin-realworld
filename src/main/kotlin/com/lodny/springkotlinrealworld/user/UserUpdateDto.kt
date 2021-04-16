package com.lodny.springkotlinrealworld.user

import javax.validation.constraints.NotBlank

class UserUpdateDto {

    @NotBlank(message = "can't be blank")
    var username = ""

    @NotBlank(message = "can't be blank")
    var email = ""

    @NotBlank(message = "can't be blank")
    var password = ""

    @NotBlank(message = "can't be blank")
    var bio = ""

    @NotBlank(message = "can't be blank")
    var image = ""

    override fun toString() = "$username, $email, $password, $bio, $image"
}
