package com.lodny.springkotlinrealworld

import com.lodny.springkotlinrealworld.article.Article
import com.lodny.springkotlinrealworld.article.ArticleService
import com.lodny.springkotlinrealworld.user.User
import com.lodny.springkotlinrealworld.user.UserService
import org.aspectj.lang.annotation.Before
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class SpringKotlinRealworldApplicationTests(
	@Autowired
	val userService: UserService,
	@Autowired
	val articleService: ArticleService
) {

	//	@Before()
//	fun init() {
//		val user = User("111", "111", "111")
//	}

	@Test
	fun contextLoads() {
		val user = User("111", "111", "111")
		userService.register(user)

		val user2 = userService.findByUsername("111")
		assertThat(user2).isEqualTo(user)

		assertThat(user2?.follows?.size).isEqualTo(0)

		val article = Article("111", "111", "111", mutableListOf(), user, "111-111")
		articleService.register(article)

		mapOf("article" to article.Article2Json(user2))
	}
}


