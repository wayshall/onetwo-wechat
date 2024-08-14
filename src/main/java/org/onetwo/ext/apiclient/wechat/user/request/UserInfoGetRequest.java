package org.onetwo.ext.apiclient.wechat.user.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author wayshall
 * <br/>
 */
@Data
@NoArgsConstructor
public class UserInfoGetRequest {
	@NotBlank
	String openid;
	String lang = "zh_CN";
	@Builder
	public UserInfoGetRequest(String openid, String lang) {
		super();
		this.openid = openid;
		if(lang != null) {
			this.lang = lang;
		}
	}
}
