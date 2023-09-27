package org.onetwo.ext.apiclient.qcloud.aiart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiartTxt2ImageRequest {
	
	String propmt;

    String negativePrompt;

    /**
    * 返回图像方式（base64 或 url) ，二选一，默认为 base64。url 有效期为1小时。
    */
    @Builder.Default
    String imgType = "base64";
    
    public boolean isUrlImage() {
    	return "url".equalsIgnoreCase(imgType);
    }

}
