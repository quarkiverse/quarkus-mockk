package io.quarkiverse.test.junit.mockk

/**
 * When used on a field of a test class, the field becomes a Mockk spy,
 * that is then used to spy on the normal scoped bean which the field represents
 */
@kotlin.annotation.Target(AnnotationTarget.FIELD)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class InjectSpy()
