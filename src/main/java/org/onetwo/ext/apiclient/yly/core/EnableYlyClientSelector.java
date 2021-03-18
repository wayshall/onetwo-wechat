package org.onetwo.ext.apiclient.yly.core;

import java.util.ArrayList;
import java.util.List;

import org.onetwo.common.apiclient.impl.RestExecutorConfiguration;
import org.onetwo.common.spring.context.AbstractImportSelector;
import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenConfiguration;
import org.onetwo.ext.apiclient.yly.EnableYilianyunClient;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wayshall
 * <br/>
 */
public class EnableYlyClientSelector extends AbstractImportSelector<EnableYilianyunClient>{

	@Override
	protected List<String> doSelect(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> classNames = new ArrayList<String>();

		classNames.add(YlyConfiguration.class.getName());
		classNames.add(AccessTokenConfiguration.class.getName());
//		classNames.add(CombineWechatConfigConfiguration.class.getName());
		classNames.add(YlyApiClentRegistrar.class.getName());
		classNames.add(RestExecutorConfiguration.class.getName());
		
		return classNames;
	}

}
