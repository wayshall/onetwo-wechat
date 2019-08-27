package org.onetwo.ext.apiclient.wxpay.vo.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.onetwo.common.convert.Types;
import org.onetwo.ext.apiclient.wechat.core.CustomizeMappingField;
import org.onetwo.ext.apiclient.wxpay.utils.WxTradeTypes;

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
public class OrderNotifyResponse extends WechatPayResponse implements CustomizeMappingField {
	
	// 以下字段在return_code为SUCCESS的时候有返回

	protected String appid;

	/***
	 * 微信支付分配的商户号
	 */
	@JsonProperty("mch_id")
	protected String mchId;

	/***
	 * 随机字符串，不长于32位
	 */
	@JsonProperty("nonce_str")
	protected String nonceStr;

	protected String sign;
	@JsonProperty("sign_type")
	protected String signType;
	
	
	
	// 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回 ，如trade_state不为 SUCCESS，则只返回out_trade_no（必传）和attach（选传）。

	/***
	 * 微信支付分配的终端设备号
	 */
	@JsonProperty("device_info")
	private String deviceInfo;

	/***
	 * 用户在商户appid下的唯一标识
	 */
	private String openid;

	/****
	 * 用户是否关注公众账号，Y-关注，N-未关注
	 */
	@JsonProperty("is_subscribe")
	private String isSubscribe;

	/***
	 * 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY，详细说明见参数规定
	 */
	@JsonProperty("trade_type")
	private WxTradeTypes tradeType;

	/***
	 * 银行类型，采用字符串类型的银行标识
	 */
	@JsonProperty("bank_type")
	@Length(max=16)
	private String bankType;

	/**
	 * 订单总金额，单位为分
	 */
	@JsonProperty("total_fee")
	private Integer totalFee;

	/****
	 * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	@JsonProperty("fee_type")
	private String feeType;

	/****
	 * 现金支付金额订单现金支付金额，详见支付金额
	 */
	@JsonProperty("cash_fee")
	private Integer cashFee;

	/***
	 * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	@JsonProperty("cash_fee_type")
	private String cashFeeType;

	/***
	 * “代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额，详见支付金额
	 */
	@JsonProperty("coupon_fee")
	private Integer couponFee;

	/***
	 * 代金券使用数量
	 */
	@JsonProperty("coupon_count")
	private Integer couponCount;
	
	private List<CouponData> coupon;

	/***
	 * 微信支付订单号	
	 */
	@JsonProperty("transaction_id")
	private String transactionId;

	@JsonProperty("out_trade_no")
	private String outTradeNo;

	/***
	 * 附加数据，原样返回
	 */
	private String attach;

	/***
	 * 订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	 */
	@JsonProperty("time_end")
	private String timeEnd;

	/***
	 * 当订单使用了免充值型优惠券后返回该参数，应结订单金额=订单金额-免充值优惠券金额。
	 */
	@JsonProperty("settlement_total_fee")
	private Integer settlementTotalFee;


	@Override
	public void mappingFields(Map<String, ?> responseMap) {
		if (this.couponCount==null || this.couponCount<1) {
			return ;
		}
		this.coupon = new ArrayList<>(this.couponCount);
		for (int i = 0; i < couponCount; i++) {
			CouponData coupon = new CouponData();
//			coupon.setIndex(i);
			coupon.setId((String)responseMap.get("coupon_id_" + i));
			coupon.setType((String)responseMap.get("coupon_type_" + i));
			coupon.setFee(Types.asValue(responseMap.get("coupon_fee_" + i), Integer.class));
			this.coupon.add(coupon);
		}
	}
	

	@Data
	public static class CouponData {
		/***
		 * 代金券ID, $n为下标，从0开始编号
		 */
		private String id;
		/***
		 * 单个代金券支付金额, $n为下标，从0开始编号
		 */
		private Integer fee;
		/***
		 * CASH--充值代金券 
NO_CASH---非充值优惠券

开通免充值券功能，并且订单使用了优惠券后有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_$0
		 */
		private String type;
//		private Integer index;
	}
}

