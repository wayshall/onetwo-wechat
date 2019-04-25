package org.onetwo.ext.apiclient.wxpay.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;
import org.onetwo.common.date.DateUtils;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.common.utils.NetUtils;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.WechatSigns;
import org.onetwo.ext.apiclient.wxpay.WechatPayBaseBootTests;
import org.onetwo.ext.apiclient.wxpay.utils.WxTradeStates;
import org.onetwo.ext.apiclient.wxpay.vo.request.OrderQueryRequest;
import org.onetwo.ext.apiclient.wxpay.vo.request.UnifiedOrderRequest;
import org.onetwo.ext.apiclient.wxpay.vo.response.OrderQueryResponse;
import org.onetwo.ext.apiclient.wxpay.vo.response.UnifiledOrderResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class PayClientTest extends WechatPayBaseBootTests {
	
	@Autowired
	PayClient payClient;
	
	@Test
	public void testOrderQuery() {
		OrderQueryRequest request = OrderQueryRequest.builder()
													.appid("wx2421b1c4370ec43b")
													.mchId("10000100")
													.nonceStr("ec2316275641faa3aacf3cc599e8730f")
													.transactionId("1008450740201411110005820873")
													.sign("FDD167FAA73459FD921B144BAF4F4CA2")
													.build();
		OrderQueryResponse res = payClient.orderQuery(request);
		System.out.println("res: " + res);
	}
	
	@Test
	public void testOrderQuery2() {
		OrderQueryRequest request = OrderQueryRequest.builder()
													.appid(appid)
													.mchId(wechatConfig.getPay().getMerchantId())
													.nonceStr("ec2316275641faa3aacf3cc599e8730f")
													.transactionId("1008450740201411110005820873")
													.build();
		

		String sign = WechatSigns.signMd5(wechatConfig.getPay().getApiKey(), request);
		request.setSign(sign);
		
		OrderQueryResponse res = payClient.orderQuery(request);
		System.out.println("res: " + res);
	}
	
	@Test
	public void testUnifieddOrder() {
		UnifiedOrderRequest request = UnifiedOrderRequest.builder()
				.body("商家-测试订单：100001")
				.outTradeNo("1111111")
				.totalFee(1)
				.spbillCreateIp(NetUtils.getHostAddress())
				.timeStart(DateUtils.format(DateUtils.DATETIME, new Date()))
				.productId("222222")
//				.openid("GZH-oA0wB1YI0nbpY0-GMZXUhv2l_ysE")
				.openid("oF0cE5qppA9lXT9y5Zdse72XXG8E")
//				.detail(detail)
				// base
				.tradeType("JSAPI")
				.appid(getWechatConfig().getAppid())
				.mchId(getWechatConfig().getPay().getMerchantId())
				.nonceStr(LangUtils.randomUUID())
				.notifyUrl("http://test.com")
				.build();
		String sign = WechatSigns.signMd5(getWechatConfig().getPay().getApiKey(), request);
		request.setSign(sign);
		UnifiledOrderResponse res = this.payClient.unifiedOrder(request);
		System.out.println("res: " + res); //wx251045584536944a87728b3d2037528850
		
	}

	@Test
	public void testOrderQueryCreated() {
		this.testOrderQuery(wechatConfig, null, "1111111");
	}

	public void testOrderQuery(WechatConfig wechatConfig, String wxTid, String outTradeNo) {
		OrderQueryRequest request = OrderQueryRequest.builder()
													.appid(wechatConfig.getAppid())
													.mchId(wechatConfig.getPay().getMerchantId())
													.nonceStr(LangUtils.randomUUID())
													.outTradeNo(outTradeNo)
													.transactionId(wxTid)
													.build();
		

		String sign = WechatSigns.signMd5(wechatConfig.getPay().getApiKey(), request);
		request.setSign(sign);
		
		OrderQueryResponse res = payClient.orderQuery(request);
		System.out.println("res: " + res);
		assertThat(res.isSuccess()).isTrue();
		assertThat(res.getTradeStateDesc()).isEqualTo("订单未支付");
		assertThat(res.getTradeState()).isEqualTo(WxTradeStates.NOTPAY);
		
		WechatSigns.checkSign(res, getWechatConfig().getPay().getApiKey(), res.getSign(), res.getSignType());
	}

}

