package org.onetwo.ext.apiclient.qcloud.nlp.interceptor;

import org.onetwo.common.apiclient.RequestContextData;
import org.onetwo.common.apiclient.interceptor.ApiInterceptor;
import org.onetwo.common.apiclient.interceptor.ApiInterceptorChain;
import org.onetwo.common.utils.NetUtils;
import org.onetwo.ext.apiclient.qcloud.nlp.NlpProperties;
import org.onetwo.ext.apiclient.qcloud.nlp.request.NlpBaseRequest;
import org.onetwo.ext.apiclient.qcloud.nlp.request.SignableData;
import org.onetwo.ext.apiclient.qcloud.nlp.util.NlpSigns;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class RequestSignInterceptor implements ApiInterceptor {
	@Autowired
	private NlpProperties nlpProperties;

	@Override
	public Object intercept(ApiInterceptorChain chain) {
		RequestContextData ctx = chain.getRequestContext();
		Object[] args = chain.getRequestContext().getMethodArgs();
		if (args.length==1 && args[0] instanceof NlpBaseRequest) {
			NlpBaseRequest request = (NlpBaseRequest) args[0];
			
			String domain = NetUtils.getHost(ctx.getRequestUrl());
			SignableData signData = SignableData.builder()
												.request(request)
												.method(ctx.getInvokeMethod().getRequestMethod().name())
												.host(domain)
												.path(ctx.getInvokeMethod().getPath())
												.build();
			String signature = NlpSigns.signHmac(nlpProperties.getSecretKey(), signData);
			request.setSignature(signature);
		}
		return chain.invoke();
	}
	

}
