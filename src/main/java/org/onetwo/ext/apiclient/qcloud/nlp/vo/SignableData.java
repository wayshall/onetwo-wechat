package org.onetwo.ext.apiclient.qcloud.nlp.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 请求方法 + 请求主机 +请求路径 + ? + 请求字符串
 * https://cloud.tencent.com/document/product/271/2053
 * @author weishao zeng
 * <br/>
 */
@Data
@Builder
public class SignableData {
	
	private String method;
	private String host;
	private String path;
	
	private NlpBaseRequest request;

}
