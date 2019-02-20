package org.onetwo.ext.apiclient.qcloud;

import java.util.ArrayList;
import java.util.List;

import org.onetwo.common.spring.context.AbstractImportSelector;
import org.onetwo.ext.apiclient.qcloud.live.QCloudLiveConfiguration;
import org.onetwo.ext.apiclient.qcloud.sms.SmsConfiguration;
import org.onetwo.ext.apiclient.qcloud.smscode.SmsCodeConfiguration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author wayshall
 * <br/>
 */
public class EnableQCloudServiceSelector extends AbstractImportSelector<EnableQCloudService>{

	@Override
	protected List<String> doSelect(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> classNames = new ArrayList<String>();

		addIfModuleEnabled(attributes, "live", QCloudLiveConfiguration.class, classNames);
		addIfModuleEnabled(attributes, "sms", SmsConfiguration.class, classNames);
		addIfModuleEnabled(attributes, "smsCode", SmsCodeConfiguration.class, classNames);
		
		return classNames;
	}
	
	private void addIfModuleEnabled(AnnotationAttributes attributes, String attrName, Class<?> configClass, List<String> classNames) {
		boolean enableLive = attributes.getBoolean(attrName);
		if (enableLive) {
			classNames.add(configClass.getName());
		}
	}

}
