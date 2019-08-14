package org.onetwo.ext.apiclient.wxpay.api;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.onetwo.common.date.DateUtils;
import org.onetwo.common.jackson.JacksonXmlMapper;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.common.utils.NetUtils;
import org.onetwo.ext.apiclient.wxpay.vo.request.OrderQueryRequest;
import org.onetwo.ext.apiclient.wxpay.vo.request.UnifiedOrderRequest;
import org.onetwo.ext.apiclient.wxpay.vo.request.UnifiedOrderRequest.GoodsDetail;
import org.onetwo.ext.apiclient.wxpay.vo.request.UnifiedOrderRequest.OrderDetail;

public class XmlMapperTest {

//	ApiclientJackson2XmlMessageConverter conveter = new ApiclientJackson2XmlMessageConverter();
	
	@Test
	public void testOrderQuery() {
		OrderQueryRequest request = OrderQueryRequest.builder()
				.appid("test_appid")
				.mchId("merchanid")
				.build();
		String xml = JacksonXmlMapper.ignoreNull().toXml(request);
		System.out.println("xml: " + xml);
		assertThat(xml).isEqualTo("<xml><appid>test_appid</appid><mch_id>merchanid</mch_id></xml>");
	}
	
	@Test
	public void testUnifiedOrderRequest() {
		GoodsDetail goodsDetail = GoodsDetail.builder()
											.goodsId("123")
											.quantity(1)
											.price(1)
											.build();
		OrderDetail detail = OrderDetail.builder()
										.goodsDetail(Arrays.asList(goodsDetail))
										.build();
		UnifiedOrderRequest request = UnifiedOrderRequest.builder()
														.body("商家-测试订单：10000")
														.outTradeNo("1111111")
														.totalFee(1)
														.spbillCreateIp("192.168.31.207")
														.timeStart("20190424094511")//(DateUtils.format(DateUtils.DATETIME, new Date()))
														.productId("222222")
														.openid("test")
														.detail(detail)
														// base
														.tradeType("JSAPI")
														.appid("test_appid")
														.mchId("3333333333")
														.nonceStr("b254118942de474ab6751ecc9d6e02c3")
														.notifyUrl("http://test.com")
														.build();
		String xml = JacksonXmlMapper.ignoreNull().toXml(request);
		System.out.println("UnifiedOrderRequest xml: " + xml);
		assertThat(xml).isEqualTo("<xml><appid>test_appid</appid><body><![CDATA[商家-测试订单：10000]]></body><detail><![CDATA[{\"goods_detail\":[{\"goods_id\":\"123\",\"quantity\":1,\"price\":1}]}]]></detail><openid>test</openid><version>1.0</version><mch_id>3333333333</mch_id><nonce_str>b254118942de474ab6751ecc9d6e02c3</nonce_str><out_trade_no>1111111</out_trade_no><total_fee>1</total_fee><spbill_create_ip>192.168.31.207</spbill_create_ip><time_start>20190424094511</time_start><notify_url>http://test.com</notify_url><trade_type>JSAPI</trade_type><product_id>222222</product_id></xml>");
	}
}

