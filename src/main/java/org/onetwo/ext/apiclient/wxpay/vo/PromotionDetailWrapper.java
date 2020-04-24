package org.onetwo.ext.apiclient.wxpay.vo;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class PromotionDetailWrapper {
	
	@JsonProperty("promotion_detail")
	List<PromotionDetailVO> promotionDetail;
	
	@Data
	static public class PromotionDetailVO {
		/***
		 * 券或者立减优惠id
		 */
		@JsonProperty("promotion_id")
		String promotionId;
		
		/****
		 * 优惠名称
		 */
		@Length(max=64)
		String name;
	
		/****
		 * 优惠范围
		 * GLOBAL- 全场代金券
	SINGLE- 单品优惠
		 */
		@Length(max=32)
		String scope;
	
		/***
		 * 优惠类型
		 * COUPON- 代金券，需要走结算资金的充值型代金券,（境外商户券币种与支付币种一致）
	DISCOUNT- 优惠券，不走结算资金的免充值型优惠券，（境外商户券币种与标价币种一致
		 */
		@Length(max=32)
		String type;
		
		/***
		 * 优惠券面额
		 */
		int amount;
		
		@JsonProperty("activity_id")
		String activityId;
		
		/****
		 * 特指由微信支付商户平台创建的优惠，出资金额等于本项优惠总金额，单位为分
		 */
		@JsonProperty("wxpay_contribute")
		int wxpayContribute;
		
		/***
		 * 特指商户自己创建的优惠，出资金额等于本项优惠总金额，单位为分
		 */
		@JsonProperty("merchant_contribute")
		int merchantContribute;
		
		/***
		 * 其他出资方出资金额，单位为分
		 */
		@JsonProperty("other_contribute")
		int other_contribute;
		
		@JsonProperty("goods_detail")
		List<GoodsDetailVO> goodsDetail;
	}
	
	@Data
	public static class GoodsDetailVO {
		/****
		 * 由半角的大小写字母、数字、中划线、下划线中的一种或几种组成
		 */
		@JsonProperty("goods_id")
		String goodsId;
		/***
		 * goods_remark为备注字段，按照配置原样返回，字段内容在微信后台配置券时进行设置。
		 */
		@JsonProperty("goods_remark")
		String goodsRemark;
		/***
		 * 商品数量
		 * 用户购买的数量
		 */
		int quantity;
		/***
		 * 商品优惠金额
		 * 单品的总优惠金额，单位为：分
		 */
		@JsonProperty("discount_amount")
		int discountAmount;
		/***
		 * 商品价格
		 * 单位为：分。如果商户有优惠，需传输商户优惠后的单价(例如：用户对一笔100元的订单使用了商场发的纸质优惠券100-50，则活动商品的单价应为原单价-50)
		 */
		int price;
	}
	
	
}
