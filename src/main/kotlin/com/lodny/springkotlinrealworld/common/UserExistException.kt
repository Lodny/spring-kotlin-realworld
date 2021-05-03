package com.lodny.springkotlinrealworld.common

import java.lang.RuntimeException

class UserExistException(val field: String, val defaultMessage: String): RuntimeException()