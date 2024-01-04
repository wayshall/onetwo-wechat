package org.onetwo.ext.apiclient.wechat.message.request;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MsgTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author LeeKITMAN
 * @author wayshall
 */
@Data
public class SendAllRequest {

    /**
     * 消息类型：mpnews（图文消息）、text（文本）、voice（语音/音频）、image（图片）、mpvideo（视频）、wxcard（微信卡券）
     */
    @NotNull
    final private MsgTypes msgtype;
    @NotNull
    private SendAllFilter filter;
    
    public SendAllRequest(MsgTypes msgtype) {
		super();
		this.msgtype = msgtype;
	}

    /**
     * 参数设置为1时，文章被判定为转载时，且原创文允许转载时，将继续进行群发操作。
     * 参数设置为0时，文章被判定为转载时，将停止群发操作。
     */
    @NotNull
    @JsonProperty("send_ignore_reprint")
    private Integer sendIgnoreReprint;

    /**
     * 开发者侧群发msgid，长度限制64字节，如不填，则后台默认以群发范围和群发内容的摘要值做为clientmsgid
     */
    private String clientmsgid;
    
    public void setFilterData(Boolean toAll, Integer tagId){
    	this.filter = new SendAllFilter(toAll, tagId);
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class SendAllFilter {

        /**
         * 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据tag_id发送给指定群组的用户
         */
        @NotNull
        @JsonProperty("is_to_all")
        private boolean toAll;

        /**
         * 群发到的标签的tag_id，参见用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id
         */
        @JsonProperty("tag_id")
        private Integer tagId;

    }
    
	@Data
	public static class MediaRequestItem {
		@JsonProperty("media_id")
	    private String mediaId;
	}
}
