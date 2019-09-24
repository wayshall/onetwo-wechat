package org.onetwo.ext.apiclient.work.message.response;

import java.util.List;

import org.onetwo.common.jackson.serializer.StringToListDerializer.VerticalSplitorToListDerializer;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 如果部分接收人无权限或不存在，发送仍然执行，但会返回无效的部分（即invaliduser或invalidparty或invalidtag），常见的原因是接收人不在应用的可见范围内。
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SendMessageResponse extends WechatResponse {
	@JsonDeserialize(using=VerticalSplitorToListDerializer.class)
	private List<String> invaliduser; //" : "userid1|userid2", // 不区分大小写，返回的列表都统一转为小写

	@JsonDeserialize(using=VerticalSplitorToListDerializer.class)
	private List<String> invalidparty; //" : "partyid1|partyid2",

	@JsonDeserialize(using=VerticalSplitorToListDerializer.class)
	private List<String> invalidtag; //":"tagid1|tagid2"
}
