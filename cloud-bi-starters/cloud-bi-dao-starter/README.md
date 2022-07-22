# cloud-bi-dao-starter

## 说明

- 提供 BaseMapper、BaseService 组件
- 提供事务处理切面

```
以下的包路径提供事务的支持，目前事务以接口的方式进行代理，所以定位到接口两层。
    org.cloud.bi
        |--package
            |--package
                |--- *Service.java
e.g.
    org.cloud.user
        |--service
            |--system
                |--PermissionService
```
