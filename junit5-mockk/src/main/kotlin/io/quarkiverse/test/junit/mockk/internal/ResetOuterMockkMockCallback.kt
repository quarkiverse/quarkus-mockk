package io.quarkiverse.test.junit.mockk.internal

import io.quarkus.test.junit.callback.QuarkusTestAfterAllCallback
import io.quarkus.test.junit.callback.QuarkusTestContext

class ResetOuterMockkMockCallback: QuarkusTestAfterAllCallback {

    override fun afterAll(context: QuarkusTestContext?) {
        context?.outerInstance?.let {
            MocksTracker.reset(it)
        }
    }
}