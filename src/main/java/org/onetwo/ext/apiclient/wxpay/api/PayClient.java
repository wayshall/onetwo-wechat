package org.onetwo.ext.apiclient.wxpay.api;

import org.onetwo.ext.apiclient.wxpay.core.WechatPayApiClient;
import org.onetwo.ext.apiclient.wxpay.vo.request.OrderQueryRequest;
import org.onetwo.ext.apiclient.wxpay.vo.request.UnifiedOrderRequest;
import org.onetwo.ext.apiclient.wxpay.vo.response.OrderQueryResponse;
import org.onetwo.ext.apiclient.wxpay.vo.response.UnifiledOrderResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author weishao zeng
 * <br/>
 */
@WechatPayApiClient
public interface PayClient {
	
	@PostMapping(path="/pay/unifiedorder", produces=MediaType.APPLICATION_XML_VALUE)
	UnifiledOrderResponse unifiedOrder(@RequestBody UnifiedOrderRequest request);
	
	@PostMapping(path="/pay/orderquery", produces=MediaType.APPLICATION_XML_VALUE)
	OrderQueryResponse orderQuery(@RequestBody OrderQueryRequest request);

}

