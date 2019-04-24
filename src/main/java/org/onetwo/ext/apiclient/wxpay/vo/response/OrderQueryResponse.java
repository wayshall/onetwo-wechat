package org.onetwo.ext.apiclient.wxpay.vo.response;

import java.util.List;

import org.onetwo.ext.apiclient.wxpay.utils.WxTradeStates;
import org.onetwo.ext.apiclient.wxpay.utils.WxTradeTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class OrderQueryResponse extends WechatPayResponse {
	
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
	
//	protected String sign_type;
	
	
	
	// 以下字段在return_code 、result_code、trade_state都为SUCCESS时有返回 ，如trade_state不为 SUCCESS，则只返回out_trade_no（必传）和attach（选传）。
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
	 * 微信支付分配的终端设备号
	 */
	@JsonProperty("device_info")
	private String device_info;

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
	private String bankType;

	/**
	 * 订单总金额，单位为分
	 */
	@JsonProperty("total_fee")
	private Integer totalFee;

	/****
	 * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	@JsonProperty("feeType")
	private String fee_type;

	/****
	 * 现金支付金额订单现金支付金额，详见支付金额
	 */
	@JsonProperty("cashFee")
	private Integer cash_fee;

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
	
	private List<CouponData> coupons;

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
	 * 对当前查询订单状态的描述和下一步操作的指引
	 */
	@JsonProperty("trade_state_desc")
	private String tradeStateDesc;
	
	/***
	 * 当订单使用了免充值型优惠券后返回该参数，应结订单金额=订单金额-免充值优惠券金额。
	 */
	@JsonProperty("settlement_total_fee")
	private Integer settlementTotalFee;

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
		private Integer index;
	}
}

