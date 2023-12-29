package org.onetwo.ext.apiclient.qcloud.api.auth;

import org.onetwo.common.apiclient.RequestContextData;
import org.onetwo.common.apiclient.interceptor.ApiInterceptor;
import org.onetwo.common.apiclient.interceptor.ApiInterceptorChain;
import org.onetwo.common.utils.NetUtils;
import org.onetwo.ext.apiclient.qcloud.QCloudProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class RequestSignInterceptor implements ApiInterceptor {
	@Autowired
	private QCloudProperties properties;

	@Override
	public Object intercept(ApiInterceptorChain chain) throws Throwable {
		RequestContextData ctx = (RequestContextData)chain.getRequestContext();
		Object[] args = ctx.getMethodArgs();
		if (args.length==1 && args[0] instanceof AuthableRequest) {
			AuthableRequest request = (AuthableRequest) args[0];
			
			String domain = NetUtils.getHost(ctx.getRequestUrl());
			SignableData signData = SignableData.builder()
												.request(request)
												.method(ctx.getInvokeMethod().getRequestMethod().name())
												.host(domain)
												.path(ctx.getInvokeMethod().getApiRequestPath(args))
												.build();
			String signature = AuthSigns.signHmac(properties.getSecretKey(), signData);
			request.setSignature(signature);
		}
		return chain.invoke();
	}
	

}
