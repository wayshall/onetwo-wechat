# onetwo-wechat

一个简单的基于Spring RestTemplate封装的微信sdk
   
## 示例项目   
示例项目，基于spring-boot
[boot-dbm-sample](https://github.com/wayshall/boot-dbm-sample)
示例项目，基于jfish
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
    <version>0.0.4-SNAPSHOT</version>
</dependency>

```
spring的依赖请自行添加。


## 配置
在使用前需要在spring的下上文（properties或yaml文件）配置下面的属性:
```Yaml   
wechat: 
    appid: 开发者ID
    appsecret: 开发者密码
    token: 令牌
    encodingAESKey: 消息加解密密钥，如果不配置，请在微信后台配置使用明文的方式

````
## 注解启用
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
- @PostMapping是spring mvc的注解，本库利用了一些spring mvc的现成注解，@PostMapping(value="/create")标识这个方法是post请求，并且请求路径是/menu下的/create。因为https://api.weixin.qq.com/cgi-bin 是@EnableWechatClient默认已经设置了的，所以在编写接口的时候，只需要用相关注解设置/menu/create即可。因为微信的创建菜单接口接收的数据类型为json，所以consumes标记本次请求的requestBody会使用application/json的MediaType。
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

### 启用和配置微信服务器地址
@EnableWechatClient 注解默认启用消息接收处理，如果不需要消息处理，可配置enableMessageServe属性为false。
```java     
  
	@EnableWechatClient
	public class SpringContextConfig {
	}   
   
```
onetwo-wechat会自动注册一系列服务，包括一个请求地址为“/serve”的Controller，所以你只需要在微信后台配置如下服务器地址：
http://你的域名/serve

### 注册消息处理器

```Java
@Component
public class MessageHandlerRegister {

	@Autowired
	MessageRouterService messageRouterService;
	
	@PostConstruct
	public void init(){
		this.messageRouterService.register(MessageType.TEXT, (TextMessage text)->{
			return TextReplyMessage.builder()
									.fromUserName(text.getToUserName())
									.toUserName(text.getFromUserName())
									.content("我收到你的消息啦~")
									.build();
		});
	}
}   

```
只需要在spring启动的时候，注入onetwo-wechat的MessageRouterService服务，并通过MessageRouterService的api注册相关类型的消息监听即可。
上面的示例会在收到消息后简单回复一条"我收到你的消息啦~"的信息。

## 微信网页授权拦截

### 基于onetwo的项目
如果是基于onetwo的项目，使用了@EnableJFishBootExtension 注解激活扩展，会自动注册拦截器。
使用的时候，通过@Interceptor注解把扩展拦截器标注在需要拦截的Controller即可：
```Java
@RestController
@Interceptor(WechatOAuth2MvcInterceptor.class)
public class TestController extends AbstractBaseController{

	@Autowired
	private WechatUserStoreService sessionStoreService;
	
	@GetMapping("/test")
	public Object test(HttpServletRequest request){
		OAuth2UserInfo userInfo = sessionStoreService.getCurrentUser(request).get();
		return userInfo;
	}

}

```

### 基于普通的spring mvc或boot的项目
如果是基于普通的spring mvc或boot的项目，则需要激活@EnableWechatClient注解的enableOAuth2Interceptor属性。
```java     
  
	@EnableWechatClient(enableOAuth2Interceptor=true)
	public class SpringContextConfig {
	}   
   
```
激活此属性后，onetwo-wechat会自动注册拦截器，拦截器url可通过配置文件wechat.oauth2.intercept.urls属性配置，默认为空，即拦截所有请求。


## 腾讯云直播支持
配置wechat.qcloud.live.enabled=true激活腾讯云的直播支持
```yaml
wechat: 
    qcloud: 
        live: 
            enabled: true
            bizId: 
            pushSafeKey: 
            callback: 
				enabled: true
                path: # 默认为：callback
```

### 监听事件消息通知
使用注解@LiveMessageListener 和@Subscribe 可以监听腾讯云的事件消息通知，详细事件见：
[https://cloud.tencent.com/document/api/267/5957](https://cloud.tencent.com/document/api/267/5957)
目前一共有三种消息：
- PushMessage ：推流 断流
- RecordingMessage ：新录制文件
- ScreenShotMessage：新截图文件
```Java
@LiveMessageListener
public class PushMessageListener {

	@Subscribe
	public void onMessage(PushMessage message) {
		if(message.isPushEvent()){
			System.out.println("有人来直播了！！！");
		}else{
			System.out.println("有人关闭了直播");
		}
	}

}
```