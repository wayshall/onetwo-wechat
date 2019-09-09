package org.onetwo.ext.apiclient.work.serve.vo.request;

import org.onetwo.ext.apiclient.wechat.serve.dto.MessageParam;

/**
 * @author weishao zeng
 * <br/>
 */
public class WorkMessageParam extends MessageParam {

	public boolean isEncryptByAes(){
		return true;
	}
}

