package io.quarkiverse.test.junit.mockk.internal

import io.quarkus.test.junit.callback.QuarkusTestAfterEachCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext

class ResetMockkMocksCallback: QuarkusTestAfterEachCallback {
    override fun afterEach(context: QuarkusTestMethodContext?) {
        context?.let { MocksTracker.reset(it.testInstance) }
    }
}