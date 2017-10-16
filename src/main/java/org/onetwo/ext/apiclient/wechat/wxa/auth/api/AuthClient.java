package org.onetwo.ext.apiclient.wechat.wxa.auth.api;

import org.onetwo.common.apiclient.annotation.InjectProperties;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;
import org.onetwo.ext.apiclient.wechat.wxa.auth.request.JscodeAuthRequest;
import org.onetwo.ext.apiclient.wechat.wxa.auth.response.JscodeAuthResponse;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * https://mp.weixin.qq.com/debug/wxadoc/dev/api/api-login.html#wxloginobject
 * @author wayshall
 * <br/>
 */
@WechatApiClient(url=UrlConst.API_DOMAIN_URL)
public interface AuthClient {

	@GetMapping(value="/sns/jscode2session")
	JscodeAuthResponse jscode2session(@InjectProperties JscodeAuthRequest request);

}
