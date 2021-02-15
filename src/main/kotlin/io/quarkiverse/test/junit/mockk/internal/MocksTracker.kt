package io.quarkiverse.test.junit.mockk.internal

import io.mockk.clearMocks
import java.util.concurrent.ConcurrentHashMap

object MocksTracker {

    private val usedMocks = ConcurrentHashMap<Any, MutableSet<Mocked>>()

    fun track(testInstance: Any, mock: Any, beanInstance: Any) {
        usedMocks.computeIfAbsent(testInstance) { mutableSetOf() }.add(Mocked(mock, beanInstance))
    }

    fun getMocks(testInstance: Any): MutableSet<Mocked> = usedMocks.getOrDefault(testInstance, mutableSetOf())

    fun reset(testInstance: Any) {
        getMocks(testInstance).map{it.mock}.forEach(::clearMocks)
    }

    data class Mocked(val mock: Any, val beanInstance: Any)
}