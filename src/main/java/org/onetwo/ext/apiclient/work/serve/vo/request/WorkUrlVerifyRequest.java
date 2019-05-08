package org.onetwo.ext.apiclient.work.serve.vo.request;

import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;

/**
 * @author weishao zeng
 * <br/>
 */
public class WorkUrlVerifyRequest extends ServeAuthParam {

	public void setMsgSignature(String msgSignature) {
		this.setSignature(msgSignature);
	}
}

