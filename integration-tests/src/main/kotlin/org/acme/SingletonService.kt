package org.acme

import jakarta.inject.Singleton

@Singleton
open class SingletonService {
    fun greet() = "Hello"
}