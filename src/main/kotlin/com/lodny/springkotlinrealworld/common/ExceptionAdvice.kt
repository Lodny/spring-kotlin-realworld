package com.lodny.springkotlinrealworld.common

import com.fasterxml.jackson.core.JsonParseException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionAdvice: ResponseEntityExceptionHandler() {

    // Argument - must remove BindingResult or Errors param
    override fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException,
                                            headers: HttpHeaders,
                                            status: HttpStatus,
                                            request: WebRequest): ResponseEntity<Any> {
        println("> ExceptionAdvice : MethodArgumentNotValidException() : $status")

        val errors: MutableMap<String, MutableList<String>> = mutableMapOf()
        e.bindingResult.fieldErrors.forEach {
            if (errors.contains(it.field))
                errors[it.field]?.add(it.defaultMessage ?: "")
            else
                errors[it.field] = mutableListOf(it.defaultMessage ?: "")
        }

        return ResponseEntity(mapOf("errors" to errors), status)
    }

    // Exception
    @ExceptionHandler(UserExistException::class)
    fun handleUserExistException(e: UserExistException): ResponseEntity<Any?> {
        println("> ExceptionAdvice : UserExistException : ")

        val errors: MutableMap<String, MutableList<String>> = mutableMapOf()
        errors[e.field] = mutableListOf(e.defaultMessage)

        return ResponseEntity(mapOf("errors" to errors), HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<Any?> {
        println("> ExceptionAdvice : NotFoundException : ")

        val errors: MutableMap<String, MutableList<String>> = mutableMapOf()
        errors[e.field] = mutableListOf(e.defaultMessage)

        return ResponseEntity(mapOf("errors" to errors), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidAuthenticationException::class)
    fun handleInvalidAuthenticationException(e: HttpMessageNotReadableException, request: WebRequest): ResponseEntity<Any?> {
        println("> ExceptionAdvice : InvalidAuthenticationException : ")

        val errors: MutableMap<String, MutableList<String>> = mutableMapOf()
        errors["token"] = mutableListOf("is wrong.")

        return ResponseEntity(mapOf("errors" to errors), HttpStatus.NOT_FOUND)
    }

//   @ExceptionHandler(JsonParseException::class)
//    fun handleJsonParseException(e: JsonParseException): ResponseEntity<Any?> {
//

//    }


    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        println("> ExceptionAdvice : HttpMessageNotReadableException : ")

        val errors: MutableMap<String, MutableList<String>> = mutableMapOf()
        errors["json"] = mutableListOf("not readable.")

        return ResponseEntity(mapOf("errors" to errors), HttpStatus.BAD_REQUEST)
    }
}

