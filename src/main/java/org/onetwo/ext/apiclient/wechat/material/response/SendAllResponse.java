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
public class SendAllResponse extends WechatResponse {
	@JsonProperty("msg_id")
    private String msgId;
	@JsonProperty("msg_data_id")
    private String msgDataId;
}
