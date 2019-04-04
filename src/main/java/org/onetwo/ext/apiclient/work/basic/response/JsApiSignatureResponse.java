package org.onetwo.ext.apiclient.work.basic.response;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class JsApiSignatureResponse {
	
    private Long timestamp;// 必填，生成签名的时间戳
    private String noncestr; // 必填，生成签名的随机串
    private String signature;

}

