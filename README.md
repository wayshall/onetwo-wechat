# onetwo-wechat



## 示例项目   
示例项目，基于spring-boot
[onetwo-wechat-sample](https://github.com/wayshall/onetwo-wechat-sample)

## JDK版本要求
1.8+

## maven
当前snapshot版本：0.0.1-SNAPSHOT

若使用snapshot版本，请添加snapshotRepository仓储：
```xml
<repository>
     <id>oss</id>
     <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>   
```

添加依赖：   
```xml

<dependency>
    <groupId>org.onetwo4j</groupId>
    <artifactId>onetwo-wechat</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>

```
spring的依赖请自行添加。



## 一行代码启用
只需要在spring配置类（即有@Configuration注解的类）上加上注解@EnableWechatClient 即可。
```java     
  
	@EnableDbm
	@EnableWechatClient
	public class SpringContextConfig {
	}   
   
```


