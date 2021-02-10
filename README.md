# Quarkus JUnit5 Mockk Extension

[![Build](https://github.com/quarkiverse/quarkus-mockk/workflows/Build/badge.svg)](https://github.com/quarkiverse/quarkus-mockk/actions?query=workflow%3ABuild)
[![Version](https://img.shields.io/maven-central/v/io.quarkiverse.mockk/quarkus-junit5-mockk?logo=apache-maven&style=for-the-badge)](https://search.maven.org/artifact/io.quarkiverse.mockk/quarkus-junit5-mockk)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This **Quarkus JUnit5 Mockk** extension allows you to easily inject mockk mocks. 

First of all, you need to add the following dependency:

```xml
<dependency>
    <groupId>io.quarkiverse.mockk</groupId>
    <artifactId>quarkus-junit5-mockk</artifactId>
    <version>0.1.0</version>
    <scope>test</scope>
</dependency>
```
If you are using gradle: 

````groovy
dependencies {
    implementation 'io.quarkiverse.mockk:quarkus-junit5-mockk:0.1.0'
}
````

