package io.quarkiverse.test.junit.mockk.internal

import io.quarkus.test.junit.QuarkusMock
import io.quarkus.test.junit.callback.QuarkusTestBeforeEachCallback
import io.quarkus.test.junit.callback.QuarkusTestMethodContext

class SetMockkMockAsBeanMockCallback: QuarkusTestBeforeEachCallback {

    override fun beforeEach(context: QuarkusTestMethodContext?) {
        context?.let {MocksTracker.getMocks(it.testInstance).forEach(::installMock)}
    }

    private fun installMock(mocked: MocksTracker.Mocked) {
        try {
            QuarkusMock.installMockForInstance(mocked.mock, mocked.beanInstance);
        } catch (e: Exception) {
            throw RuntimeException("""$mocked.beanInstance
                     is not a normal scoped CDI bean, make sure the bean is a normal scope like @ApplicationScoped or @RequestScoped.
                     Alternatively you can use '@InjectMock(convertScopes=true)' instead of '@InjectMock' if you would like
                     Quarkus to automatically make that conversion (you should only use this if you understand the implications).""".trimIndent());
        }
    }
}