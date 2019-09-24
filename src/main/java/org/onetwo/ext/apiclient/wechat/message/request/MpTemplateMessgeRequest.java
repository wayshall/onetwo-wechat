package org.onetwo.ext.apiclient.wechat.message.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MpTemplateMessgeRequest extends MpTemplateMessge {
	
	private String touser;

	@Builder
	public MpTemplateMessgeRequest(String templateId, String url, MiniprogramData miniprogram, String touser) {
		super(templateId, url, miniprogram);
		this.touser = touser;
	}

}

