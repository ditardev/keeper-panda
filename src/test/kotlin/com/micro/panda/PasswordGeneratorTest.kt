package com.micro.panda

import com.micro.panda.service.utils.PasswordGenerator
import org.junit.jupiter.api.Test

class PasswordGeneratorTest {

    @Test
    fun testPasswordGeneration() {
        for (i in 1..10) {
            val result: String = PasswordGenerator().generatePassword("Aaaaaaaaaa00000")
            println(result)
        }
    }

}