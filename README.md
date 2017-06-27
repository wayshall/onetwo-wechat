# onetwo-wechat



## 示例项目   
示例项目，基于spring-boot
[onetwo-wechat-sample](https://github.com/wayshall/onetwo-wechat-sample)

## 要求
- JDK 1.8+
- Spring 4.0+  


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
onetwo-wechat把微信的rest客户端接口和消息接收处理服务端接口分开启用。
如果只需要使用微信的客户端rest接口，只需要在spring配置类（即有@Configuration注解的类）上加上注解@EnableWechatClient 即可。
```java     
  
	@EnableWechatClient
	public class SpringContextConfig {
	}   
   
```

## 自定义微信接口
因为微信接口众多，目前只实现了少量接口。所以onetwo-wechat提供了一阵自定义实现微信客户端接口的方式。   

未实现的接口可按下面的方式实现：   
包约定：
- 根据微信模块建立相应的包，比如要实现菜单的接口，则创建对应的xxx.menu。   
- 接口放在api子包下，即：xxx.menu.api
- 请求的vo放在request子包下，即：xxx.menu.request
- 响应的vo放在response子包下，即：xxx.menu.response

### 创建自定义的微信接口

比如创建菜单接口
根据微信的创建菜单接口文档，其http请求地址为：
https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN   

对应接口代码如下：
```Java
@WechatApiClient(path="/menu")
public interface MenuService {
	
	@PostMapping(value="/create", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@WechatRequestConfig(accessToken=true)
	WechatResponse create(@RequestBody CreateMenuRequest createMenuRequest);

}
```   

解释：   
- 所有微信客户端接口必须使用@WechatApiClient注解标注为微信接口
- @WechatApiClient注解的path指定本接口下的所有方法的请求路径都是“/menu”的子路径
- @PostMapping是spring mvc的注解，本库利用了一些spring mvc的现成注解，@PostMapping(value="/create")标识这个方法是post请求，并且请求路径是/menu下的/create。因为https://api.weixin.qq.com/cgi-bin是@EnableWechatClient默认已经设置了的，所以在编写接口的时候，只需要用相关注解设置/menu/create即可。因为微信的创建菜单接口接收的数据类型为json，所以consumes标记本次请求的requestBody会使用application/json的MediaType。
- @RequestBody 表示createMenuRequest参数将会作为请求的request body
- @WechatRequestConfig(accessToken=true)注解的意思是自动获取accessToken并附加到请求的url参数。
- CreateMenuRequest 是一个VO，表示请求内容，按照微信的文档编写即可。
- WechatResponse 是一个VO，表示响应内容，按照微信的文档编写即可，这里使用了onetwo-wechat自带的基类。

至此，一个微信rest客户端接口就编写完成了。

### 使用接口
使用的时候，只需要直接把接口用spring的方式直接注入示例即可使用，如：
```Java

public class MenuServiceTest {
	
	@Autowired
	MenuService menuService;
	
	@Test
	public void testCreateMenu() throws IOException{
		String classPath = "menu_create.json";

		CreateMenuRequest request = ....;
		WechatResponse res = menuService.create(request);
		assertThat(res.isSuccess()).isTrue();
	}

}
```

## 消息接收处理