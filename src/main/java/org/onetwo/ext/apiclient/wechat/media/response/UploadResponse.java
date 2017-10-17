package org.onetwo.ext.apiclient.wechat.media.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UploadResponse extends WechatResponse {
	
	String url;

}
