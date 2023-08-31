# Quarkus JUnit5 MockK Extension

[![GitHub Actions Status](<https://img.shields.io/github/workflow/status/quarkiverse/quarkus-mockk/Build?logo=GitHub&style=for-the-badge>)](https://github.com/quarkiverse/quarkus-mockk/actions?query=workflow%3ABuild)
[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.mockk/quarkus-junit5-mockk?logo=apache-maven&style=for-the-badge)](https://search.maven.org/artifact/io.quarkiverse.mockk/quarkus-junit5-mockk)
[![License](https://img.shields.io/github/license/quarkusio/quarkus?style=for-the-badge&logo=apache)](https://www.apache.org/licenses/LICENSE-2.0)

## Description

This Quarkus JUnit5 MockK extension allows you to easily inject MockK mocks.

The full documentation be found [here](https://quarkiverse.github.io/quarkiverse-docs/quarkus-mockk/dev/index.html).

## Importing the dependency

First of all, you need to add the following dependency:

```xml
<dependency>
    <groupId>io.quarkiverse.mockk</groupId>
    <artifactId>quarkus-junit5-mockk</artifactId>
    <version>LATEST</version>
    <scope>test</scope>
</dependency>
```
If you are using gradle: 

````groovy
dependencies {
    testImplementation 'io.quarkiverse.mockk:quarkus-junit5-mockk:LATEST'
}
````

## Compatibility with Quarkus

Starting with version `3+`, version `2.0.0` of `quarkus-junit5-mockk` should be used.
If you use a version between `2.8` and before `3.0.0`, version `1.1.0` of `quarkus-junit5-mockk` should be used.
If you use a version before `2.7`, version `1.0.1` of `quarkus-junit5-mockk` should be used.

## Example

Now, you can use `@InjectMock` and `@InjectSpy` in your test such as: 

````kotlin
@QuarkusTest
class InjectionMockTest {

    @Inject
    private lateinit var firstService: FirstService

    @InjectMock
    private lateinit var secondService: SecondService

    @Test
    fun `should respond test`() {
        every { secondService.greet() } returns "test"
        assertThat(firstService.greet()).isEqualTo("test")
    }

    @Test
    fun `should respond second`() {
        every { secondService.greet() } returns "second"
        assertThat(firstService.greet()).isEqualTo("second")
        verify { secondService.greet() }
    }
}
````
