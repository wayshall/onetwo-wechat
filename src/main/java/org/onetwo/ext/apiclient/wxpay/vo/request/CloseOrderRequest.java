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
public class CloseOrderRequest extends BasePayRequest {
	
	@JsonProperty("out_trade_no")
	private String outTradeNo;

	@Builder
	public CloseOrderRequest(String appid, String mchId, String nonceStr, String sign, String signType,
			String outTradeNo) {
		super(appid, mchId, nonceStr, sign, signType);
		this.outTradeNo = outTradeNo;
	}
	
}

