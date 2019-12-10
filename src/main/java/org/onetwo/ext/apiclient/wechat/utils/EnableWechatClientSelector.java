package org.onetwo.ext.apiclient.wechat.utils;

import java.util.ArrayList;
import java.util.List;

import org.onetwo.common.apiclient.impl.RestExecutorConfiguration;
import org.onetwo.common.spring.context.AbstractImportSelector;
import org.onetwo.ext.apiclient.wechat.EnableWechatClient;
import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenConfiguration;
import org.onetwo.ext.apiclient.wechat.accesstoken.CombineWechatConfigConfiguration;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClentRegistrar;
import org.onetwo.ext.apiclient.wechat.core.WechatSupportConfiguration;
import org.onetwo.ext.apiclient.wechat.oauth2.WechatOAuth2Configuration;
import org.onetwo.ext.apiclient.wechat.serve.WechatServeConfiguration;
import org.onetwo.ext.apiclient.wechat.serve.web.WechatMvcConfigurerAdapter;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wayshall
 * <br/>
 */
public class EnableWechatClientSelector extends AbstractImportSelector<EnableWechatClient>{

	@Override
	protected List<String> doSelect(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> classNames = new ArrayList<String>();
		classNames.add(WechatApiClentRegistrar.class.getName());
		classNames.add(RestExecutorConfiguration.class.getName());
		classNames.add(AccessTokenConfiguration.class.getName());
		classNames.add(CombineWechatConfigConfiguration.class.getName());
		classNames.add(WechatSupportConfiguration.class.getName());
		
		
		//oauth2 support
		classNames.add(WechatOAuth2Configuration.class.getName());
		boolean enableMessageServe = attributes.getBoolean("enableMessageServe");
		if(enableMessageServe){
			classNames.add(WechatServeConfiguration.class.getName());
		}
		if(attributes.getBoolean("enableOAuth2Interceptor")){
			classNames.add(WechatMvcConfigurerAdapter.class.getName());
		}
		

//		classNames.add(WechatSecurityOAuth2Configuration.class.getName());
		
		return classNames;
	}

}
