package io.quarkiverse.test.junit.mockk.internal

import io.quarkus.test.junit.QuarkusMock
import io.quarkus.test.junit.callback.QuarkusTestBeforeEachCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext

class SetMockkMockAsBeanMockCallback: QuarkusTestBeforeEachCallback {

    override fun beforeEach(context: QuarkusTestMethodContext?) {
        context?.let {MocksTracker.getMocks(it.testInstance).forEach(::installMock)}
    }

    private fun installMock(mocked: MocksTracker.Mocked) {
        QuarkusMock.installMockForInstance(mocked.mock, mocked.beanInstance)
    }
}