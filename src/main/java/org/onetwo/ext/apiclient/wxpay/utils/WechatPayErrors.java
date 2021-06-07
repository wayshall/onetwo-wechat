package org.onetwo.ext.apiclient.wxpay.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.onetwo.common.exception.ErrorType;

import com.google.common.collect.Maps;

/**
 * 错误码查询工具：https://open.work.weixin.qq.com/devtool/query?e=60123
 * 
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2
 * 
 * @author wayshall
 * <br/>
 */
public enum WechatPayErrors implements ErrorType {
	ORDER_NOT_EXIST("ORDERNOTEXIST", "此交易订单号不存在"),
	SYSTEM_ERROR("SYSTEMERROR", "系统错误"),
	ORDERPAID("ORDERPAID", "该订单已支付"),
	ORDERCLOSED("ORDERCLOSED", "订单已关闭，无法重复关闭"),
	SIGNERROR("SIGNERROR", "参数签名结果不正确"),
	REQUIRE_POST_METHOD("REQUIRE_POST_METHOD", "未使用post传递参数"),
	XML_FORMAT_ERROR("XML_FORMAT_ERROR", "XML格式错误"),
	;

	private static final Map<String, WechatPayErrors> ERROR_MAP;
	
	static {
		Map<String, WechatPayErrors> temp = Maps.newHashMapWithExpectedSize(WechatPayErrors.values().length);
		for(WechatPayErrors err : WechatPayErrors.values()){
			temp.put(err.errcode, err);
		}
		ERROR_MAP = Collections.unmodifiableMap(temp);
	}
	
	private String errcode;
	private String errmsg;

	private WechatPayErrors(String errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	@Override
	public String getErrorCode() {
		return errcode.toString();
	}

	@Override
	public String getErrorMessage() {
		return errmsg;
	}
	
	public static Optional<WechatPayErrors> byErrcode(String errcode){
		return Optional.ofNullable(ERROR_MAP.get(errcode));
	}
}
