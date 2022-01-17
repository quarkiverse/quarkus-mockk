package io.quarkiverse.test.junit.mockk

/**
 * When used on a field of a test class, the field becomes a Mockk mock,
 * that is then used to mock the normal scoped bean which the field represents
 *
 * If {@code convertScopes} is set to true, then Quarkus will change the scope of the target {@code Singleton} bean to {@code ApplicationScoped}
 * to make the mockable.
 * This is an advanced setting and should only be used if you don't rely on the differences between {@code Singleton}
 * and {@code ApplicationScoped} beans (for example it is invalid to read fields of {@code ApplicationScoped} beans
 * as a proxy stands in place of the actual implementation)
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class InjectMock(val relaxed: Boolean = false, val relaxUnitFun: Boolean = false,
        val convertScopes: Boolean = false)
