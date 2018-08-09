package org.onetwo.ext.apiclient.wechat.material.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author LeeKITMAN
 * @author wayshall
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class AddMaterialResponse extends WechatResponse {
	private String url;
	
	@JsonProperty("media_id")
    private String mediaId;
}
