package org.onetwo.ext.apiclient.wechat.wxa.api;

import javax.validation.Valid;

import org.onetwo.common.apiclient.annotation.ResponseHandler;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.handler.ByteArrayResponseHandler;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;
import org.onetwo.ext.apiclient.wechat.wxa.request.BaseQrcodeRequest.CreatewxaqrcodeRequest;
import org.onetwo.ext.apiclient.wechat.wxa.request.BaseQrcodeRequest.GetwxacodeRequest;
import org.onetwo.ext.apiclient.wechat.wxa.request.BaseQrcodeRequest.GetwxacodeunlimitRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 为满足不同需求和场景，这里提供了三个接口，开发者可挑选适合自己的接口。 
 * A接口，生成小程序码，可接受path参数较长，生成个数受限。 
 * B接口，生成小程序码，可接受页面参数较短，生成个数不受限。
 * C接口，生成二维码，可接受path参数较长，生成个数受限。
 * https://developers.weixin.qq.com/miniprogram/dev/api/qrcode.html
 * @author wayshall
 * <br/>
 */
@WechatApiClient(url=UrlConst.API_DOMAIN_URL)
public interface QrcodeClient {

	/***
	 * 接口A: 适用于需要的码数量较少的业务场景 
	 * 接口地址：
	 * https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN
	 * 通过该接口生成的小程序码，永久有效，数量限制见文末说明，请谨慎使用。用户扫描该码进入小程序后，将直接进入 path 对应的页面。
	 * @author wayshall
	 * @return
	 */
	@PostMapping(value="/wxa/getwxacode", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseHandler(ByteArrayResponseHandler.class)
	ByteArrayResource getwxacode(AccessTokenInfo accessToken, @Valid @RequestBody GetwxacodeRequest request);

	/***
	 * 接口B：适用于需要的码数量极多的业务场景
	 * 
	 * 注意：通过该接口生成的小程序码，永久有效，数量暂无限制。
	 * 用户扫描该码进入小程序后，开发者需在对应页面获取的码中 scene 字段的值，再做处理逻辑。使用如下代码可以获取到二维码中的 scene 字段的值。
	 * 调试阶段可以使用开发工具的条件编译自定义参数 scene=xxxx 进行模拟，开发工具模拟时的 scene 的参数值需要进行 urlencode
	 * @author wayshall
	 * @param accessToken
	 * @param request
	 * @return
	 */
	@PostMapping(value="/wxa/getwxacodeunlimit", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseHandler(ByteArrayResponseHandler.class)
	ByteArrayResource getwxacodeunlimit(AccessTokenInfo accessToken, @Valid @RequestBody GetwxacodeunlimitRequest request);
	
	/***
	 * 接口C：适用于需要的码数量较少的业务场景
	 * 注意：通过该接口生成的小程序二维码，永久有效，数量限制见文末说明，请谨慎使用。用户扫描该码进入小程序后，将直接进入 path 对应的页面。
	 * https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode
	 * @author wayshall
	 * @param accessToken
	 * @param request
	 * @return
	 */
	@PostMapping(value="/cgi-bin/wxaapp/createwxaqrcode", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseHandler(ByteArrayResponseHandler.class)
	ByteArrayResource createwxaqrcode(AccessTokenInfo accessToken, @Valid @RequestBody CreatewxaqrcodeRequest request);
	
}
