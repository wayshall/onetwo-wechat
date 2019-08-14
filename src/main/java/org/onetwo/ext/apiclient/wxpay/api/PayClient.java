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
	
	/****
	 * 同一个商户订单号，不能修改金额、商品描述等关键字段，否则会提示：商户订单号重复
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	@PostMapping(path="/pay/unifiedorder", produces=MediaType.APPLICATION_XML_VALUE, consumes=MediaType.APPLICATION_XML_VALUE)
	UnifiledOrderResponse unifiedOrder(@RequestBody UnifiedOrderRequest request);
	
	/***
	 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	@PostMapping(path="/pay/orderquery", produces=MediaType.APPLICATION_XML_VALUE, consumes=MediaType.APPLICATION_XML_VALUE)
	OrderQueryResponse orderQuery(@RequestBody OrderQueryRequest request);

}

