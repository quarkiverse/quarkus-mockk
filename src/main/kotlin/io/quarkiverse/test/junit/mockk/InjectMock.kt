package io.quarkiverse.test.junit.mockk



/**
 * When used on a field of a test class, the field becomes a Mockk mock,
 * that is then used to mock the normal scoped bean which the field represents
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class InjectMock(val relaxed: Boolean = false, val relaxUnitFun: Boolean = false)
