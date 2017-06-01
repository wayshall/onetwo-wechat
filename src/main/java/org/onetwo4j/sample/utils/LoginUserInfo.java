package org.onetwo4j.sample.utils;

import java.util.Collection;

import org.onetwo.ext.security.utils.LoginUserDetails;
import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class LoginUserInfo extends LoginUserDetails {
	
	private String mobile;
	private String nickName;
	private String avartarPath;

	public LoginUserInfo(long userId, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(userId, username, password, authorities);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvartarPath() {
		return avartarPath;
	}

	public void setAvartarPath(String avartarPath) {
		this.avartarPath = avartarPath;
	}
}
