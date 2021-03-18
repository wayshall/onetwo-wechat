package org.onetwo.ext.apiclient.qcloud.trtc;

import org.onetwo.ext.apiclient.qcloud.trtc.service.TrtcSignService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实时音视频服务
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties(TrtcProperties.class)
//@ConditionalOnProperty(TrtcProperties.ENABLED_KEY)
public class TrtcConfiguration {
	
	@Bean
	@ConditionalOnMissingBean(TrtcSignService.class)
	public TrtcSignService trtcSignService() {
		return new TrtcSignService();
	}

}
