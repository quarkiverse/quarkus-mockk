package org.acme

import javax.inject.Singleton

@Singleton
open class SingletonService {
    fun greet() = "Hello"
}