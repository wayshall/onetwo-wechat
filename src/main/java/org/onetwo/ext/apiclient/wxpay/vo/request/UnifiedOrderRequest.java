package org.onetwo.ext.apiclient.wxpay.vo.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import org.onetwo.ext.apiclient.wxpay.utils.OrderDetailSerializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文档： https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 * 
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class UnifiedOrderRequest extends BasePayRequest {

	/***
	 * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
	 */
	@JsonProperty("device_info")
	@Length(max=32)
	private String deviceInfo;
	
	/****
	 * 商品简单描述，该字段请按照规范传递，具体请见参数规定
	 * PC网站		扫码支付		浏览器打开的网站主页title名 -商品概述	腾讯充值中心-QQ会员充值	
	      微信浏览器		公众号支付	商家名称-销售商品类目					腾讯-游戏
	 */
	@JacksonXmlCData
	@Length(max=128)
	private String body;

	/***
	 * 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
	 * max=6000
	 */
	@JacksonXmlCData
	@JsonSerialize(using=OrderDetailSerializer.class)
	private OrderDetail detail;

	/****
	 * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
	 */
	@JacksonXmlCData
	@Length(max=127)
	private String attach;

	/***
	 * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号
	 */
	@NotNull
	@NotBlank
	@Length(max=32)
	@JsonProperty("out_trade_no")
	private String outTradeNo;

	/***
	 * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
	 */
	@JsonProperty("fee_type")
	private String feeType;

	/****
	 * 订单总金额，单位为分，详见支付金额
	 */
	@NotNull
	@JsonProperty("total_fee")
	private Integer totalFee;

	/***
	 * 支持IPV4和IPV6两种格式的IP地址。调用微信支付API的机器IP
	 * 如：123.12.12.123
	 */
	@NotNull
	@NotBlank
	@Length(max=64)
	@JsonProperty("spbill_create_ip")
	private String spbillCreateIp;

	/***
	 * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	 */
	@JsonProperty("time_start")
	private String timeStart;

	/***
	 * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id。其他详见时间规则
	建议：最短失效时间间隔大于1分钟
	 */
	@JsonProperty("time_expire")
	private String timeExpire;


	/****
	 * https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_102&index=2
	 * 
	 * 	订单优惠标记，用于区分订单是否可以享受优惠，字段内容在微信后台配置券时进行设置，说明详见代金券或立减优惠
	 */
	@JsonProperty("goods_tag")
	@Length(max=32)
	private String goodsTag;

	/***
	 * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
	 */
	@NotNull
	@NotBlank
	@Length(max=256)
	@JsonProperty("notify_url")
	private String notifyUrl;

	/****
	 * JSAPI -JSAPI支付
NATIVE -Native支付
APP -APP支付
说明详见参数规定
	 */
	@NotNull
	@NotBlank
	@Length(max=16)
	@JsonProperty("trade_type")
	private String tradeType = "JSAPI";

	/***
	 * trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
	 */
	@Length(max=32)
	@JsonProperty("product_id")
	private String productId;

	/***
	 * 上传此参数no_credit--可限制用户不能使用信用卡支付
	 */
	@Length(max=32)
	@JsonProperty("limit_pay")
	private String limitPay;

	/***
	 * trade_type=JSAPI时（即JSAPI支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。
	 * openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
	 */
	private String openid;

	/***
	 * 	Y，传入Y时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
	 */
	private String receipt;

	/***
	 * 该字段常用于线下活动时的场景信息上报，支持上报实际门店信息，商户也可以按需求自己上报相关信息。该字段为JSON对象数据，对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }} ，字段详细说明请点击行前的+展开
	 */
	@Length(max=256)
	@JsonProperty("scene_info")
	@JacksonXmlCData
	private String sceneInfo;
	
	
	/***
	 * 新增字段，接口版本号，区分原接口，默认填写1.0。入参新增version后，则支付通知接口也将返回单品优惠信息字段promotion_detail，请确保支付通知的签名验证能通过。
	 */
	@NotNull
	@NotBlank
	@Length(max=32)
	private String version = "1.0";
	
	/*****
	 * https://pay.weixin.qq.com/wiki/doc/api/allocation_sl.php?chapter=24_3&index=3
	 * 是否指定服务商分账
	 * Y-是，需要分账
N-否，不分账
字母要求大写，不传默认不分账
	 */
	@Length(max=16)
	@JsonProperty("profit_sharing")
	private String profitSharing;

	@Builder
	public UnifiedOrderRequest(String appid, String mchId, String nonceStr, String sign, String signType,
			String deviceInfo, String body, OrderDetail detail, String attach, String outTradeNo, String feeType,
			Integer totalFee, String spbillCreateIp, String timeStart, String timeExpire, String goodsTag,
			String notifyUrl, String tradeType, String productId, String limitPay, String openid, String receipt,
			String sceneInfo) {
		super(appid, mchId, nonceStr, sign, signType);
		this.deviceInfo = deviceInfo;
		this.body = body;
		this.detail = detail;
		this.attach = attach;
		this.outTradeNo = outTradeNo;
		this.feeType = feeType;
		this.totalFee = totalFee;
		this.spbillCreateIp = spbillCreateIp;
		this.timeStart = timeStart;
		this.timeExpire = timeExpire;
		this.goodsTag = goodsTag;
		this.notifyUrl = notifyUrl;
		this.tradeType = tradeType;
		this.productId = productId;
		this.limitPay = limitPay;
		this.openid = openid;
		this.receipt = receipt;
		this.sceneInfo = sceneInfo;
	}

	
	/****
	 * https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_102&index=2
	 * 
	 * @author way
	 *
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class OrderDetail {
		/****
		 * 1.商户侧一张小票订单可能被分多次支付，订单原价用于记录整张小票的交易金额。
2.当订单原价与支付金额不相等，则不享受优惠。
3.该字段主要用于防止同一张小票分多次支付，以享受多次优惠的情况，正常支付订单不必上传此参数。
		 */
		@JsonProperty("cost_price")
		private Integer costPrice;
		/***
		 * 商家小票ID
		 */
		@JsonProperty("receipt_id")
		private String receiptId;
		
		/***
		 * 单品信息，使用Json数组格式提交
		 */
		@JsonProperty("goods_detail")
		private List<GoodsDetail> goodsDetail;
		
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class GoodsDetail {
		/***
		 * 由半角的大小写字母、数字、中划线、下划线中的一种或几种组成
		 */
		@NotNull
		@NotBlank
		@JsonProperty("goods_id")
		@Length(max=32)
		private String goodsId; 
		/***
		 * 微信支付定义的统一商品编号（没有可不传）
		 */
		@JsonProperty("wxpay_goods_id")
		@Length(max=32)
		private String wxpayGoodsId; // 

		/***
		 * 商品的实际名称
		 */
		@Length(max=256)
		@JsonProperty("goods_name")
		private String goodsName; 

		/***
		 * 用户购买的数量
		 */
		@NotNull
		private Integer quantity; 

		/***
		 * 单位为：分。如果商户有优惠，需传输商户优惠后的单价(例如：用户对一笔100元的订单使用了商场发的纸质优惠券100-50，则活动商品的单价应为原单价-50)
		 */
		@NotNull
		private Integer price;
		
	}


}

