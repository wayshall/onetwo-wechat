package org.onetwo.ext.apiclient.wxpay.api;

import org.onetwo.ext.apiclient.wxpay.core.WechatPayApiClient;
import org.onetwo.ext.apiclient.wxpay.vo.request.CloseOrderRequest;
import org.onetwo.ext.apiclient.wxpay.vo.request.OrderQueryRequest;
import org.onetwo.ext.apiclient.wxpay.vo.request.UnifiedOrderRequest;
import org.onetwo.ext.apiclient.wxpay.vo.response.CloseOrderResponse;
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
	 * 普通支付：
	 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
	 * 分账支付：
	 * https://pay.weixin.qq.com/wiki/doc/api/allocation_sl.php?chapter=24_3&index=3
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
	
	/***
	 * 以下情况需要调用关单接口：商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
		注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。

	 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_3
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	@PostMapping(path="/pay/closeorder", produces=MediaType.APPLICATION_XML_VALUE, consumes=MediaType.APPLICATION_XML_VALUE)
	CloseOrderResponse closeOrder(@RequestBody CloseOrderRequest request);

}

