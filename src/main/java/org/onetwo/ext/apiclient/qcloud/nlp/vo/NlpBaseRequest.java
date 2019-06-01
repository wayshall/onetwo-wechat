package org.onetwo.ext.apiclient.qcloud.nlp.vo;

import org.onetwo.common.annotation.FieldName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
//@NoArgsConstructor
@AllArgsConstructor
public class NlpBaseRequest {
	/***
	 * 接口指令的名称，例如: LexicalAnalysis
	 */
	@FieldName("Action")
	String action;

	/***
	 * 区域参数，用来标识希望操作哪个区域的实例。可选: 
bj:北京
sh:上海
hk:香港
gz:广州
ca:北美
	 */
	@FieldName("Region")
	String region;

	/***
	 * 当前UNIX时间戳
	 */
	@FieldName("Timestamp")
	int timestamp;
	/****
	 * 随机正整数，与 Timestamp 联合起来, 用于防止重放攻击
	 */
	@FieldName("Nonce")
	int nonce;
	/***
	 * 由腾讯云平台上申请的标识身份的 SecretId 和 SecretKey, 其中 SecretKey 会用来生成 Signature
具体参考 接口鉴权 页面
	 */
	@FieldName("SecretId")
	String secretId;

	/***
	 * 请求签名，用来验证此次请求的合法性, 
具体参考 接口鉴权 页面
	 */
	@FieldName("Signature")
	String signature;

}
