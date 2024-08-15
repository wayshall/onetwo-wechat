package org.onetwo.ext.apiclient.baidu.core;

import java.util.ArrayList;
import java.util.List;

import org.onetwo.common.apiclient.impl.RestExecutorConfiguration;
import org.onetwo.common.spring.context.AbstractImportSelector;
import org.onetwo.ext.apiclient.baidu.EnableBaiduClient;
import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenConfiguration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wayshall
 * <br/>
 */
public class EnableBaiduClientSelector extends AbstractImportSelector<EnableBaiduClient>{

	@Override
	protected List<String> doSelect(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> classNames = new ArrayList<String>();

		classNames.add(BaiduApiConfiguration.class.getName());
		classNames.add(AccessTokenConfiguration.class.getName());
//		classNames.add(CombineWechatConfigConfiguration.class.getName());
		classNames.add(BaiduApiClentRegistrar.class.getName());
		classNames.add(RestExecutorConfiguration.class.getName());
		
		return classNames;
	}

}
