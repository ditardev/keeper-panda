package com.micro.panda.service.utils

import org.springframework.stereotype.Component
import java.util.Random
import java.util.regex.Pattern

@Component
class PasswordGenerator(
    val lowCase: String = "qwertyuiopasdfghjklzxcvbnm",
    val nums: String = "0123456789",
    val pattern: String = "Rezcjlwige27690",

    val minimumSize: Int = 3,
    val maximumSize: Int = 49,

    private val random: Random = Random()
) {
    private val upperCasePattern = Pattern.compile("[A-Z]")
    private val lowerCasePattern = Pattern.compile("[a-z]")
    private val numberPattern = Pattern.compile("\\d")

    fun generatePassword(inputExample: String?): String {
        val templateArray = inputExample?.takeIf {
            it.length in minimumSize until maximumSize
        }?.toCharArray() ?: pattern.toCharArray()
        return StringBuilder().apply {
            for (c in templateArray) {
                append(
                    when {
                        upperCasePattern.matcher(c.toString()).find() -> getRandomChar(lowCase.uppercase())
                        lowerCasePattern.matcher(c.toString()).find() -> getRandomChar(lowCase)
                        numberPattern.matcher(c.toString()).find() -> getRandomChar(nums)
                        else -> c
                    }
                )
            }
        }.toString()
    }

    private fun getRandomChar(input: String): Char = input[random.nextInt(input.length)]
}