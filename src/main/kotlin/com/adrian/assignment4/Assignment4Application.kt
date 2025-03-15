package com.adrian.assignment4

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Assignment4Application

@RestController
class HelloController {
	@GetMapping("/")
	fun hello() = "Hello! The application is running!"
}

fun main(args: Array<String>) {
	runApplication<Assignment4Application>(*args)
}
