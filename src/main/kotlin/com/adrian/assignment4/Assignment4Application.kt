package com.adrian.assignment4

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EntityScan("com.adrian.assignment4")
@EnableJpaRepositories("com.adrian.assignment4")
class Assignment4Application

@RestController
class HelloController {
	@GetMapping("/")
	fun hello() = "Hello! The application is running!"

	@GetMapping("/public/hello")
	fun publicHello() = "This is a public endpoint!"

	@GetMapping("/secure/hello")
	fun secureHello() = "This is a secure endpoint - you must be authenticated to see this!"
}

fun main(args: Array<String>) {
	runApplication<Assignment4Application>(*args)
}
