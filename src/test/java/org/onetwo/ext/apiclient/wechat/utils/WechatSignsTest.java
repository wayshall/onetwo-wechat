package org.onetwo.ext.apiclient.wechat.utils;
/**
 * @author weishao zeng
 * <br/>
 */

import org.junit.Test;
import org.onetwo.common.md.CodeType;
import org.onetwo.common.md.Hashs;

public class WechatSignsTest {
	
	@Test
	public void testSign() {
		String source = "appid=ww25a3bee2b14fad93&attach=&bank_type=HXB_CREDIT&cash_fee=1&fee_type=CNY&is_subscribe=N&mch_id=1521104291&nonce_str=6egokMJdfFjWl7B6&openid=otiEv1V5QmXg2Hc010XOUDlYw72g&out_trade_no=329081235357831168&result_code=SUCCESS&return_code=SUCCESS&return_msg=OK&time_end=20190524163638&total_fee=1&trade_state=SUCCESS&trade_state_desc=支付成功&trade_type=JSAPI&transaction_id=4200000329201905244145399426";
		source = "appid=ww25a3bee2b14fad93&bank_type=HXB_CREDIT&cash_fee=1&fee_type=CNY&is_subscribe=N&mch_id=1521104291&nonce_str=6egokMJdfFjWl7B6&openid=otiEv1V5QmXg2Hc010XOUDlYw72g&out_trade_no=329081235357831168&result_code=SUCCESS&return_code=SUCCESS&return_msg=OK&time_end=20190524163638&total_fee=1&trade_state=SUCCESS&trade_state_desc=支付成功&trade_type=JSAPI&transaction_id=4200000329201905244145399426&key=MicroCloudTECH2017GuangZhouTianH";
		String res = Hashs.md5(false, CodeType.HEX).hash(source);
		System.out.println("res: " + res);
	}

}

