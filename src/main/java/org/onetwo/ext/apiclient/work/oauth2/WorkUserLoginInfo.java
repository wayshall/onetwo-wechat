package org.onetwo.ext.apiclient.work.oauth2;

import java.util.Map;

import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;

import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
//@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkUserLoginInfo implements OAuth2User {
	private String appid;
	private String userId;
	private String deviceId;
	
    /***
     * 附加属性
     */
	@Builder.Default
    Map<String, Object> attachProperties = Maps.newHashMap();
}

