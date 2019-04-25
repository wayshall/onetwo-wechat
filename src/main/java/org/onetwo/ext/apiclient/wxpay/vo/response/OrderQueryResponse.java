package org.onetwo.ext.apiclient.wxpay.vo.response;

import org.onetwo.ext.apiclient.wechat.core.CustomizeMappingField;
import org.onetwo.ext.apiclient.wxpay.utils.WxTradeStates;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@ToString(callSuper=true)
public class OrderQueryResponse extends OrderNotifyResponse implements CustomizeMappingField {

	/***
	 * SUCCESS—支付成功

REFUND—转入退款

NOTPAY—未支付

CLOSED—已关闭

REVOKED—已撤销（付款码支付）

USERPAYING--用户支付中（付款码支付）

PAYERROR--支付失败(其他原因，如银行返回失败)

支付状态机请见下单API页面
	 */
	@JsonProperty("trade_state")
	private WxTradeStates tradeState;
	
	/***
	 * 对当前查询订单状态的描述和下一步操作的指引
	 */
	@JsonProperty("trade_state_desc")
	private String tradeStateDesc;
	
}

