# Quarkus JUnit5 Mockk Extension

[![GitHub Actions Status](<https://img.shields.io/github/workflow/status/quarkiverse/quarkus-mockk/Build?logo=GitHub&style=for-the-badge>)](https://github.com/quarkiverse/quarkus-mockk/actions?query=workflow%3ABuild)
[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.mockk/quarkus-junit5-mockk?logo=apache-maven&style=for-the-badge)](https://search.maven.org/artifact/io.quarkiverse.mockk/quarkus-junit5-mockk)
[![License](https://img.shields.io/github/license/quarkusio/quarkus?style=for-the-badge&logo=apache)](https://www.apache.org/licenses/LICENSE-2.0)

## Description

This Quarkus JUnit5 Mockk extension allows you to easily inject mockk mocks.

The full documentation be found [here](https://quarkiverse.github.io/quarkiverse-docs/quarkus-mockk/dev/index.html).

## Importing the dependency

First of all, you need to add the following dependency:

```xml
<dependency>
    <groupId>io.quarkiverse.mockk</groupId>
    <artifactId>quarkus-junit5-mockk</artifactId>
    <version>0.2.0</version>
    <scope>test</scope>
</dependency>
```
If you are using gradle: 

````groovy
dependencies {
    implementation 'io.quarkiverse.mockk:quarkus-junit5-mockk:0.2.0'
}
````

## Example

Now, you can use `@InjectMock` and `@InjectSpy` in your test such as: 

````kotlin
@QuarkusTest
class InjectionMockTest {

    @Inject
    lateinit var firstService: FirstService

    @InjectMock
    lateinit var secondService: SecondService

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