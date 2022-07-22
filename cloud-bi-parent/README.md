# cloud-bi-parent
父组件，Spring Boot 项目需要将父组件指向本组件。如：

```xml
<parent>
    <groupId>org.cloud</groupId>
    <artifactId>cloud-bi-parent</artifactId>
    <version>1.0.0</version>
</parent>
```

本组件以不透传不相关的组件到项目为原则，只做版本控制。使用者需要某种类别的功能时，只需要引入本项目提供的 starter 即可。