package com.kokoo.stomp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StompApplication

fun main(args: Array<String>) {
	runApplication<StompApplication>(*args)
}
