package org.onetwo.ext.apiclient.wxpay.utils;

import org.onetwo.common.utils.Assert;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.onetwo.ext.apiclient.wechat.utils.WechatSigns;
import org.onetwo.ext.apiclient.wxpay.vo.ClientPayParameter;

/**
 * @author wayshall <br/>
 */
public abstract class WechatPayUtils {
	
	public static final String API_DOMAIN_URL = "https://api.mch.weixin.qq.com";
	
	abstract public static class PayResponseFields {
		public static final String KEY_ERRCODE = "return_code";
		public static final String KEY_ERRMSG = "return_msg";
		public static final String KEY_RESULT_CODE = "result_code";
		public static final String KEY_ERR_CODE = "err_code";
		public static final String KEY_ERR_CODE_DES = "err_code_des";
	}
	
	public static ClientPayParameter signPrepay(String appId, String prepayId, String signKey) {
		Assert.hasText(appId, "appId must has text");
		Assert.hasText(prepayId, "prepayId must has text");
		Assert.hasText(signKey, "signKey must has text");
		
		ClientPayParameter p = new ClientPayParameter();
		p.setAppId(appId);
		p.setNonceStr(LangUtils.randomUUID());
		p.setPrepayId(prepayId);
		p.setSignType(WechatConstants.SIGN_MD5);
		p.setTimeStamp(String.valueOf(System.currentTimeMillis()/1000)); //秒
		
		String paySign = WechatSigns.sign(signKey, p);
		p.setPaySign(paySign);
		return p;
	}
}
