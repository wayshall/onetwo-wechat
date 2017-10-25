package org.onetwo.ext.apiclient.wechat.serve.web;

import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.oauth2.OAuth2SpringMvcInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author wayshall
 * <br/>
 */
public class WechatMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Autowired
	WechatConfig wechatConfig;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if(LangUtils.isEmpty(wechatConfig.getOauth2InterceptUrls())){
			registry.addInterceptor(oauth2SpringMvcInterceptor());
		}else{
			registry.addInterceptor(oauth2SpringMvcInterceptor())
					.addPathPatterns(wechatConfig.getOauth2InterceptUrls());
		}
	}
	
	@Bean
	public HandlerInterceptor oauth2SpringMvcInterceptor(){
		return new OAuth2SpringMvcInterceptor();
	}
}
