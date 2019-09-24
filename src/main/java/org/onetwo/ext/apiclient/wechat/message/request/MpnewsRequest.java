package org.onetwo.ext.apiclient.wechat.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MsgTypes;

/**
 * 图文消息（注意图文消息的media_id需要通过上述方法来得到）：
 * {
   "filter":{
      "is_to_all":false,
      "tag_id":2
   },
   "mpnews":{
      "media_id":"123dsdajkasd231jhksad"
   },
    "msgtype":"mpnews",
    "send_ignore_reprint":0
}
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class MpnewsRequest extends SendAllRequest {

    private MediaRequestItem mpnews = new MediaRequestItem();

	public MpnewsRequest() {
		super(MsgTypes.MPNEWS);
	}

}
