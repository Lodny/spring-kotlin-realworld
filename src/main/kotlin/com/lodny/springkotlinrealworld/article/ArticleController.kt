package com.lodny.springkotlinrealworld.article

import com.github.slugify.Slugify
import com.lodny.springkotlinrealworld.common.NotFoundException
import com.lodny.springkotlinrealworld.common.mandatoryUser
import com.lodny.springkotlinrealworld.common.optionalUser
import com.lodny.springkotlinrealworld.tag.TagService
import com.lodny.springkotlinrealworld.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional

import org.springframework.web.bind.annotation.*
import javax.servlet.ServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = ["http://localhost:3000"])
class ArticleController(val articleService: ArticleService,
                        val userService: UserService,
                        val tagService: TagService) {

//    @PersistenceContext
//    private EntityManager em;
    //CrudRepository EntityManager

    private fun randomAlphaNumString(len: Int) =  (('A'..'Z') + ('0'..'9')).shuffled().subList(0, len).joinToString("")

    @Transactional
    @GetMapping("")
    fun findAll(@RequestParam(required = false) author: String?,
                @RequestParam(required = false) favorited: String?,
                @RequestParam(required = false) tag: String?,
                @RequestParam(defaultValue = "10") limit: Int,
                @RequestParam(defaultValue = "0") offset: Int,
                request: ServletRequest): ResponseEntity<Any?> {

        println("ArticleController.findAll : author = $author, favorited = $favorited, tag = $tag, limit = $limit, offset = $offset")

//        if (limit == null || offset == null)
//            return ResponseEntity("bad request", HttpStatus.BAD_REQUEST)

        val loginUser = optionalUser(request)
        val articles = when {
                author != null -> articleService.findByAuthor(userService.findByUsername(author)!!, limit, offset)
                favorited != null -> {
                    val user = userService.findByUsername(favorited) ?: throw NotFoundException("user", "$favorited is not found.")
                    articleService.getFavorites(user.favorites, limit, offset)
                }
                tag != null -> articleService.getArticlesByTag(tag, limit, offset)
                else -> articleService.findAll(limit, offset)
        }

        println("articles.size = ${articles.size}")
        return ResponseEntity(mapOf("articles" to articles.map { it.Article2Json(loginUser) }), HttpStatus.OK)
    }

    @GetMapping("/feed")
    fun getFeeds(@RequestParam limit: Int, @RequestParam offset: Int, request: ServletRequest): ResponseEntity<Any?> {
        println("ArticleController.getFeeds : limit = $limit, offset = $offset")

        val loginUser = mandatoryUser(request)
        val articles = articleService.getFeeds(loginUser.follows, limit, offset)

        return ResponseEntity(mapOf("articles" to articles.map { it.Article2Json(loginUser) }), HttpStatus.OK)
    }

    @GetMapping("/{slug}")
    fun findBySlug(@PathVariable slug: String, request: ServletRequest): ResponseEntity<Any?> {
        println("ArticleController.findBySlug")

        val loginUser = optionalUser(request)
        val article = articleService.findBySlug(slug)

        return ResponseEntity(mapOf("article" to article.Article2Json(loginUser)), HttpStatus.OK)
    }

    @PostMapping("")
    fun register(@Valid @RequestBody articleDto: ArticleRegisterDto, request: ServletRequest): ResponseEntity<Any?> {
        println("articles[POST] : register : $articleDto")

        val loginUser = mandatoryUser(request)
        var article = Article(articleDto, loginUser, "${Slugify().slugify(articleDto.title)}-${randomAlphaNumString(8)}")
        article = articleService.register(article)

        tagService.update(article.tagList)

        return ResponseEntity(mapOf("article" to article.Article2Json(loginUser)), HttpStatus.CREATED)
    }

    @PutMapping("/{slug}")
    fun update(@Valid @RequestBody articleDto: ArticleRegisterDto, @PathVariable slug: String, request: ServletRequest): ResponseEntity<Any?> {
        println("articles[PUT] : update : $articleDto")

        val loginUser = mandatoryUser(request)
        var article = articleService.findBySlug(slug)

        if (article.tagList != articleDto.tagList)
            tagService.update(article.tagList)

        article.update(articleDto,"${Slugify().slugify(articleDto.title)}-${randomAlphaNumString(8)}")
        if (!articleService.notFindBySlug(article.slug))        // 8자리 랜덤 값이 중복되면 다시 한번 시도
            article.update(articleDto,"${Slugify().slugify(articleDto.title)}-${randomAlphaNumString(8)}")

        article = articleService.update(article)
        return ResponseEntity(mapOf("article" to article.Article2Json(loginUser)), HttpStatus.OK)
    }

    @DeleteMapping("/{slug}")
    fun delete(@PathVariable slug: String, request: ServletRequest): ResponseEntity<Any?> {
        println("ArticleController.delete: slug = $slug")

        mandatoryUser(request)
        articleService.deleteBySlug(slug)

        return ResponseEntity("{}", HttpStatus.OK)
    }

    @PostMapping("/{slug}/favorite")
    fun favorite(@PathVariable slug: String, request: ServletRequest): ResponseEntity<Any?> {
        println("ArticleController.favorite")

        var loginUser = mandatoryUser(request)
        var article = articleService.findBySlug(slug)

        if (loginUser.favorites.contains(article)) {
            loginUser.favorites.remove(article)
            article.favoritesCount--
        } else {
            loginUser.favorites.add(article)
            article.favoritesCount++
        }
        loginUser = userService.update(loginUser)

        article = articleService.update(article)

        return ResponseEntity(mapOf("article" to article.Article2Json(loginUser)), HttpStatus.OK)
    }
}