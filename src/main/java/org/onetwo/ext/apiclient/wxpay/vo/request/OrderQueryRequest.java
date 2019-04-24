package org.onetwo.ext.apiclient.wxpay.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Builder
public class OrderQueryRequest extends BasePayRequest {
	

	@JsonProperty("transaction_id")
	private String transactionId;

	@JsonProperty("out_trade_no")
	private String outTradeNo;

	@Builder
	public OrderQueryRequest(String appid, String mchId, String nonceStr, String sign, String sign_type,
			String transactionId, String outTradeNo) {
		super(appid, mchId, nonceStr, sign, sign_type);
		this.transactionId = transactionId;
		this.outTradeNo = outTradeNo;
	}
	
}

