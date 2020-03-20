package org.onetwo.ext.apiclient.yly.core;

import java.util.UUID;

import org.onetwo.common.annotation.FieldName;
import org.onetwo.common.md.Hashs;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@NoArgsConstructor
public class YlyRequest {
	@FieldName("client_id")
	String clientId;
	int timestamp;
	String sign;
	String id;
	
	public YlyRequest(String clientId, int timestamp, String sign, String id) {
		super();
		this.clientId = clientId;
		this.timestamp = timestamp;
		this.sign = sign;
		this.id = id;
	}
	
	public void sign(YlyAppConfig appConfig) {
		sign(appConfig.getAppConfig(getClientId()).getAppsecret());
	}
	
	public void sign(String appsecret) {
		this.timestamp = (int)(System.currentTimeMillis() / 1000);
		String signSource = getClientId() + timestamp + appsecret;
		sign  = Hashs.MD5.hash(signSource).toLowerCase();
		if (id==null) {
			id = UUID.randomUUID().toString();
		}
	}
}
