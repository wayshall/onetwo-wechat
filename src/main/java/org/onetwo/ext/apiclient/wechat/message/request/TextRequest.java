package org.onetwo.ext.apiclient.wechat.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MsgTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 文本：

{
   "filter":{
      "is_to_all":false,
      "tag_id":2
   },
   "text":{
      "content":"CONTENT"
   },
    "msgtype":"text"
}

 * @author LeeKITMAN
 * @author wayshall
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class TextRequest extends SendAllRequest {

    private RequestItem text = new RequestItem();

	public TextRequest() {
		super(MsgTypes.TEXT);
	}
	
	@Data
	public static class RequestItem {
		@JsonProperty("content")
	    private String content;
	}
}
