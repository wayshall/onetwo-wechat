package org.onetwo.ext.apiclient.qcloud.aiart.vo;

import lombok.Data;

@Data
public class AiartTxt2ImageResponse {

    /**
    * 根据入参 RspImgType 填入不同，返回不同的内容。
如果传入 base64 则返回生成图 Base64 编码。
如果传入 url 则返回的生成图 URL , 有效期1小时，请及时保存。
    */
	String imageUrl;
	String imageData;
	
}
