package org.onetwo.ext.apiclient.wechat.material.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author LeeKITMAN
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AddNewsResponse extends WechatResponse {

	@JsonProperty("media_id")
    private String mediaId;
}
