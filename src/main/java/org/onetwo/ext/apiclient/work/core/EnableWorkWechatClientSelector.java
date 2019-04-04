package org.onetwo.ext.apiclient.work.core;

import java.util.ArrayList;
import java.util.List;

import org.onetwo.common.apiclient.impl.RestExecutorConfiguration;
import org.onetwo.common.spring.context.AbstractImportSelector;
import org.onetwo.ext.apiclient.work.EnableWorkWechatClient;
import org.onetwo.ext.apiclient.work.WorkWechatConfiguration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wayshall
 * <br/>
 */
public class EnableWorkWechatClientSelector extends AbstractImportSelector<EnableWorkWechatClient>{

	@Override
	protected List<String> doSelect(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> classNames = new ArrayList<String>();
		classNames.add(WorkWechatApiClentRegistrar.class.getName());
		classNames.add(RestExecutorConfiguration.class.getName());
		
		classNames.add(WorkWechatSupportConfiguration.class.getName());
		classNames.add(WorkWechatConfiguration.class.getName());
		
		return classNames;
	}

}
