# spring-security-demo

## 一、涉及到的技术

### 1.1、 jwt
认证令牌
### 1.2、 swagger
接口文档
### 1.3、 redis
存储用户信息

## 二、版本信息

### 2.1、spring-boot
```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.5.RELEASE</version>
</parent>
```
### 2.2、mysql
```
8.0.13
```
### 2.3、jdk
```
1.8
```
## 三、运行
### 3.1、访问swagger
启动项目，在浏览器访问localhost:9000/swagger-ui.html即可看到swagger页面
### 3.2、用户登录
选择登陆接口，默认的三个用户（admin，user1，user2）的密码都是123456
### 3.3、测试接口
点击swagger右上角的Authorize按钮，将登陆返回的token信息填入，然后对相应的接口进行认证权限测试
