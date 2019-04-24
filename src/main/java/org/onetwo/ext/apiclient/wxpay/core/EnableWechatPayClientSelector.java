package org.onetwo.ext.apiclient.wxpay.core;

import java.util.ArrayList;
import java.util.List;

import org.onetwo.common.apiclient.impl.RestExecutorConfiguration;
import org.onetwo.common.spring.context.AbstractImportSelector;
import org.onetwo.ext.apiclient.wxpay.EnableWechatPayClient;
import org.onetwo.ext.apiclient.wxpay.WechatPayConfiguration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wayshall
 * <br/>
 */
public class EnableWechatPayClientSelector extends AbstractImportSelector<EnableWechatPayClient>{

	@Override
	protected List<String> doSelect(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> classNames = new ArrayList<String>();
		classNames.add(WechatPayApiClentRegistrar.class.getName());
		classNames.add(RestExecutorConfiguration.class.getName());
		classNames.add(WechatPayConfiguration.class.getName());
		
		return classNames;
	}

}
