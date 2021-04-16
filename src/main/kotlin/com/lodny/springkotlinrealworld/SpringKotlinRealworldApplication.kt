package com.lodny.springkotlinrealworld

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringKotlinRealworldApplication

fun main(args: Array<String>) {
	runApplication<SpringKotlinRealworldApplication>(*args)

	println(">>> Start My Kotlin Realworld~~~")
}
