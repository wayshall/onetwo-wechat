package org.onetwo.ext.apiclient.wechat.serve.dto;

import java.util.HashMap;

import org.onetwo.ext.apiclient.wechat.serve.spi.Message;

/**
 * @author weishao zeng
 * <br/>
 */
@SuppressWarnings("serial")
public class MapMessage extends HashMap<String, String> implements Message {

	private FlowType flowType;
	
	@Override
	public String getToUserName() {
		return get("ToUserName");
	}

	@Override
	public String getFromUserName() {
		return get("FromUserName");
	}

	@Override
	public String getMsgType() {
		return get("MsgType");
	}

	@Override
	public FlowType getFlowType() {
		if (this.flowType==null) {
			return FlowType.RECEIVE;
		}
		return flowType;
	}

}

