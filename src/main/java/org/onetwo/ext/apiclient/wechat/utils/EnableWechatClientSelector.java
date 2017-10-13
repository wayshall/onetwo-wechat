package org.onetwo.ext.apiclient.wechat.utils;

import java.util.ArrayList;
import java.util.List;

import org.onetwo.common.spring.context.AbstractImportSelector;
import org.onetwo.ext.apiclient.wechat.EnableWechatClient;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClentRegistrar;
import org.onetwo.ext.apiclient.wechat.core.WechatSupportConfiguration;
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
		classNames.add(WechatSupportConfiguration.class.getName());
		boolean enableMessageServe = attributes.getBoolean("enableMessageServe");
		if(enableMessageServe){
			classNames.add(WechatServeConfiguration.class.getName());
		}
		if(attributes.getBoolean("enableOAuth2Interceptor")){
			classNames.add(WechatMvcConfigurerAdapter.class.getName());
		}
		return classNames;
	}

}
