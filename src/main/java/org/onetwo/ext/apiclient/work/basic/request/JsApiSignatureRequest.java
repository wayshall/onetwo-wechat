package org.onetwo.ext.apiclient.work.basic.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsApiSignatureRequest {
	
    private Long timestamp;// 必填，生成签名的时间戳
    private String noncestr; // 必填，生成签名的随机串
    private String url;

}

