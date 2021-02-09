# Quarkus JUnit5 Mockk Extension

This **Quarkus JUnit5 Mockk** extension allows you to easily inject mockk mocks. 

First of all, you need to add the following dependency:

```xml
<dependency>
    <groupId>io.quarkiverse.mockk</groupId>
    <artifactId>quarkus-junit5-mockk</artifactId>
    <version>0.0.1</version>
    <scope>test</scope>
</dependency>
```
If you are using gradle: 

````groovy
dependencies {
    implementation 'io.quarkiverse.mockk:quarkus-junit5-mockk:0.0.1'
}
````

Once the dependency is imported, you can use `@InjectMock` and `@InjectSpy` annotations such as:

```java

```


## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

 <!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
 <!-- prettier-ignore-start -->
 <!-- markdownlint-disable -->
 <table>
   <tr>
     <td align="center"><a href="https://github.com/zhfeng"><img src="https://avatars2.githubusercontent.com/u/1246139?v=4" width="100px;" alt=""/><br /><sub><b>Amos Feng</b></sub></a><br /><a href="https://github.com/quarkiverse/quarkiverse-mybatis/commits?author=zhfeng" title="Code">💻</a> <a href="#maintenance-zhfeng" title="Maintenance">🚧</a></td>
   </tr>
 </table>

 <!-- markdownlint-enable -->
 <!-- prettier-ignore-end -->
 <!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!