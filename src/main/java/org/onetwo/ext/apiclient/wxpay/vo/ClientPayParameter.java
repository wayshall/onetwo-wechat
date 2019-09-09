package org.onetwo.ext.apiclient.wxpay.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 客户端支付所需参数
 * 
 * @author weishao zeng
 * <br/>
 */
@Data
public class ClientPayParameter {
	/***
	 * //公众号id	appId	是	String(16)	wx8888888888888888	商户注册具有支付权限的公众号成功后即可获得
	 */
	private String appId; 
	/****
	 * 时间戳	timeStamp	是	String(32)	1414561699	当前的时间，其他详见时间戳规则
	 */
	private String timeStamp;
	/***
	 * 随机字符串	nonceStr	是	String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	随机字符串，不长于32位。推荐随机数生成算法
	 */
	private String nonceStr;
	/***
	 * 订单详情扩展字符串	package	是	String(128)	prepay_id=123456789	统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
	 */
	@JsonProperty("package")
	private String packageStr;
	/****
	 * 签名方式	signType	是	String(32)	MD5	签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
	 */
	private String signType = "MD5";
	/***
	 * 签名	paySign	是	String(64)	C380BEC2BFD727A4B6845133519F3AD6	签名，详见签名生成算法
	 */
	private String paySign;
	
	public void setPrepayId(String prepayId) {
		this.packageStr = "prepay_id=" + prepayId;
	}
}

