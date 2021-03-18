package org.onetwo.ext.apiclient.work.basic.response;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class JsApiSignatureResponse {
	
	private String appid;
    private Long timestamp;// 必填，生成签名的时间戳
    private String noncestr; // 必填，生成签名的随机串
    private String signature;
    
    /***
     * js端nonceStr字段是驼峰命名，为了避免坑，多返回一个驼峰命名的字段
     */
	public String getNonceStr() {
		return noncestr;
	}
	public void setNonceStr(String nonceStr) {
		this.noncestr = nonceStr;
	}

}

