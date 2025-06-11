package com.micro.panda

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PandaApplication

fun main(args: Array<String>) {
	runApplication<PandaApplication>(*args)
}
