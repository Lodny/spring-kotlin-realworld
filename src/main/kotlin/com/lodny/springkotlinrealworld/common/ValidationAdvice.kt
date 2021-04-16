package com.lodny.springkotlinrealworld.common

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import java.util.function.Consumer


@Component
@Aspect
class ValidationAdvice {
    @Around("execution(* com.lodny.springkotlinrealworld..*Controller.*(..))")
    @Throws(Throwable::class)
    fun validCheck(joinPoint: ProceedingJoinPoint): Any? {
        val args = joinPoint.args
//        println("> ValidationAdvice : validCheck()")

        for (arg in args) {
//            println("> ValidationAdvice : validCheck() : type of arg : $arg")
            if (arg !is BindingResult || !arg.hasErrors()) {
                continue
            }

            val errors = mutableMapOf<String, MutableList<String>>()
            arg.fieldErrors.forEach {
                if (errors.containsKey(it.field))
                    errors.get(it.field)!!.add(it?.defaultMessage ?: "")
                else
                    errors.put(it.field, mutableListOf(it?.defaultMessage ?: ""))
            }

            println(">>> ValidationAdvice : validCheck() : Error : $errors");
            return ResponseEntity<Any>(mapOf("errors" to errors), HttpStatus.BAD_REQUEST)
        }

        // System.out.println("> ValidationAdvice : validCheck() : NO Error");
        return joinPoint.proceed()
    }

}