package org.onetwo.ext.apiclient.wechat.utils;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Test;
import org.onetwo.common.file.FileUtils;
import org.onetwo.common.jackson.JacksonXmlMapper;
import org.onetwo.common.md.CodeType;
import org.onetwo.common.md.Hashs;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClientResponseHandler;
import org.onetwo.ext.apiclient.wxpay.vo.response.OrderNotifyResponse;
import org.onetwo.ext.apiclient.wxpay.vo.response.OrderQueryResponse;
import org.springframework.core.io.Resource;

public class WechatSignsTest {
	
	@Test
	public void testCheckWithSha1() {
		String signKey = "HyVFkGl5F5OQWJZZaNzBBg==";
		String rawData = "{\"nickName\":\"Band\",\"gender\":1,\"language\":\"zh_CN\",\"city\":\"Guangzhou\",\"province\":\"Guangdong\",\"country\":\"CN\",\"avatarUrl\":\"http://wx.qlogo.cn/mmopen/vi_32/1vZvI39NWFQ9XM4LtQpFrQJ1xlgZxx3w7bQxKARol6503Iuswjjn6nIGBiaycAjAtpujxyzYsrztuuICqIM5ibXQ/0\"}";
		String hashData = "75e81ceda165f4ffa64f4068af58c64b8f54b88c";
		boolean res = WechatSigns.checkWithSha1(signKey, rawData, hashData);
		assertThat(res).isTrue();

		
		signKey = "LT6ujkSocI+dVZU3SVRRSw==";
		rawData = "{\"nickName\":\"Jpeng41\",\"avatarUrl\":\"http://sf1-ttcdn-tos.pstatp.com/img/mosaic-legacy/3791/5070639578~120x256.image\",\"gender\":0,\"city\":\"\",\"province\":\"\",\"country\":\"中国\",\"language\":\"\"}";
		hashData = "f47945b505af11b96b1fe989d4d1e4537786ad8c";
		res = WechatSigns.checkWithSha1(signKey, rawData, hashData);
		assertThat(res).isTrue();
		
		
		signKey = "9BJzsuFvPNLcub00/jRi4Q==";
		rawData = "{\"nickName\":\"用户7642462741864\",\"avatarUrl\":\"http://sf1-ttcdn-tos.pstatp.com/img/mosaic-legacy/3795/3044413937~120x256.image\",\"gender\":0,\"city\":\"\",\"province\":\"\",\"country\":\"中国\",\"language\":\"\"}";
		hashData = "7684e5fa81e2d82831a81eaf20de11a79ea45be8";
		res = WechatSigns.checkWithSha1(signKey, rawData, hashData);
		assertThat(res).isTrue();
	}
	
	@Test
	public void testSign() {
		String source = "appid=ww25a3bee2b14fad93&attach=&bank_type=HXB_CREDIT&cash_fee=1&fee_type=CNY&is_subscribe=N&mch_id=1521104291&nonce_str=6egokMJdfFjWl7B6&openid=otiEv1V5QmXg2Hc010XOUDlYw72g&out_trade_no=329081235357831168&result_code=SUCCESS&return_code=SUCCESS&return_msg=OK&time_end=20190524163638&total_fee=1&trade_state=SUCCESS&trade_state_desc=支付成功&trade_type=JSAPI&transaction_id=4200000329201905244145399426";
		source = "appid=ww25a3bee2b14fad93&bank_type=HXB_CREDIT&cash_fee=1&fee_type=CNY&is_subscribe=N&mch_id=1521104291&nonce_str=6egokMJdfFjWl7B6&openid=otiEv1V5QmXg2Hc010XOUDlYw72g&out_trade_no=329081235357831168&result_code=SUCCESS&return_code=SUCCESS&return_msg=OK&time_end=20190524163638&total_fee=1&trade_state=SUCCESS&trade_state_desc=支付成功&trade_type=JSAPI&transaction_id=4200000329201905244145399426&key=MicroCloudTECH2017GuangZhouTianH";
		String res = Hashs.md5(false, CodeType.HEX).hash(source);
		System.out.println("res: " + res);
	}
	
	@Test
	public void testWxpayQueryOrderSign() throws Exception {
		Resource res = SpringUtils.classpath("data/wxpay_order_query.xml");
		String xml = FileUtils.readAsString(res.getInputStream());
		System.out.println("xml:" + xml);
		
		Map<String, Object> dataMap = JacksonXmlMapper.defaultMapper().fromXml(xml, Map.class);
		WechatApiClientResponseHandler handler = new WechatApiClientResponseHandler();
		OrderQueryResponse orderQuery = (OrderQueryResponse)handler.handleResponseMap(dataMap, OrderQueryResponse.class);
		System.out.println("orderQuery:" + orderQuery);

		String signKey = "test";
		
		String sourceString = WechatSigns.convertToSourceString(signKey, orderQuery);
		System.out.println("sourceString:" + sourceString);
		assertThat(sourceString).isEqualTo("appid=wwtestAPPID&bank_type=GDB_CREDIT&cash_fee=10600&cash_fee_type=CNY&coupon_count=3&coupon_fee=300&coupon_fee_0=100&coupon_fee_1=100&coupon_fee_2=100&coupon_id_0=7017662831&coupon_id_1=7019535215&coupon_id_2=7019535494&fee_type=CNY&is_subscribe=Y&mch_id=1111111&nonce_str=bzs1qLeJUV1IO1Y0&openid=oCCdi5to_OPENID&out_trade_no=362299258075877376&result_code=SUCCESS&return_code=SUCCESS&return_msg=OK&time_end=20190823152921&total_fee=10900&trade_state=SUCCESS&trade_state_desc=支付成功&trade_type=JSAPI&transaction_id=4200000394201908239032592598&key=test");
		
		String sign = WechatSigns.signMd5(signKey, orderQuery);
		System.out.println("sign:" + sign);
		assertThat(sign).isEqualTo("CE80E9FBE27E59F793F8DC44FFE449DF");
	}
	

	@Test
	public void testWxpayNotifyOrderSign() throws Exception {
		Resource res = SpringUtils.classpath("data/wxpay_notify.xml");
		String xml = FileUtils.readAsString(res.getInputStream());
		System.out.println("xml:" + xml);
		
		Map<String, Object> dataMap = JacksonXmlMapper.defaultMapper().fromXml(xml, Map.class);
		WechatApiClientResponseHandler handler = new WechatApiClientResponseHandler();
		OrderNotifyResponse orderNotifyResponse = (OrderNotifyResponse)handler.handleResponseMap(dataMap, OrderNotifyResponse.class);
		System.out.println("OrderNotifyResponse:" + orderNotifyResponse);

		String signKey = "test";
		
		String sourceString = WechatSigns.convertToSourceString(signKey, orderNotifyResponse);
		System.out.println("sourceString:" + sourceString);
		
		WechatSigns.checkSign(orderNotifyResponse, signKey, orderNotifyResponse.getSign(), orderNotifyResponse.getSignType());
	}

}

