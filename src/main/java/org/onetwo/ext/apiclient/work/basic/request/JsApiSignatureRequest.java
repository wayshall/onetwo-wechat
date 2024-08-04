package org.onetwo.ext.apiclient.work.basic.request;

import javax.validation.constraints.NotBlank;
import org.onetwo.common.annotation.IgnoreField;

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
	
    private Long timestamp;// 客户端不填时，服务器端生成。调用微信接口时必填，生成签名的时间戳
    private String noncestr; // 客户端不填时，服务器端生成。调用微信接口时必填，生成签名的随机串
    @NotBlank
    private String url;
    
    /***
     * 调用微信接口的时候不需要此参数，主要用户对多appid的支持
    
    @IgnoreField
    private String appid; */

}

