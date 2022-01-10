package org.onetwo.ext.apiclient.wechat.formid;
/**
 * @author weishao zeng
 * <br/>
 */

import java.io.Serializable;

import org.onetwo.common.web.userdetails.GenericUserDetail;

@SuppressWarnings("serial")
public class CollectFormIdEvent implements Serializable {
	private GenericUserDetail<?> loginUser;
    private String formId;
    private Long timestamp;
    
	public CollectFormIdEvent(GenericUserDetail<?> loginUser, String formId, Long timestamp) {
		super();
		this.formId = formId;
		this.timestamp = timestamp;
		this.loginUser = loginUser;
	}

	public GenericUserDetail<?> getLoginUser() {
		return loginUser;
	}

	public String getFormId() {
		return formId;
	}

	public Long getTimestamp() {
		return timestamp;
	}
    
}
