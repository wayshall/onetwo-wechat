package org.onetwo.ext.apiclient.wxpay.utils;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author weishao zeng
 * <br/>
 */
@AllArgsConstructor
public enum WxTradeStates {
    	SUCCESS("支付成功"),
    	REFUND("转入退款"),
    	NOTPAY("未支付"),
    	CLOSED("已关闭"),
    	REVOKED("已撤销（付款码支付）"),
    	USERPAYING("用户支付中（付款码支付）"),
    	PAYERROR("支付失败(其他原因，如银行返回失败)");
    	
    	@Getter
    	private String label;
    	
    	public static WxTradeStates of(String state) {
    		return Stream.of(values()).filter(s -> s.name().equals(state))
    				.findAny()
    				.orElseThrow(() -> new IllegalArgumentException("error wxpay state: " + state));
    	}
}

