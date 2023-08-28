package io.quarkiverse.test.junit.mockk.internal.application

import jakarta.inject.Singleton

@Singleton
open class SimpleSingletonBean {

    fun greet(name: String) = "hello $name"

}