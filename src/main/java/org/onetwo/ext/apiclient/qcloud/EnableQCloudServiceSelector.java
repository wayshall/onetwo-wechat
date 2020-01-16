package org.onetwo.ext.apiclient.qcloud;

import java.util.ArrayList;
import java.util.List;

import org.onetwo.common.apiclient.impl.RestExecutorConfiguration;
import org.onetwo.common.spring.context.AbstractImportSelector;
import org.onetwo.ext.apiclient.qcloud.live.QCloudLiveConfiguration;
import org.onetwo.ext.apiclient.qcloud.nlp.NlpApiClentRegistrar;
import org.onetwo.ext.apiclient.qcloud.nlp.NlpConfiguration;
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
		
		classNames.add(QCloudBaseConfiguration.class.getName());
		
		classNames.add(RestExecutorConfiguration.class.getName());

		addIfModuleEnabled(attributes, "nlp", () -> {
			classNames.add(NlpApiClentRegistrar.class.getName());
			classNames.add(NlpConfiguration.class.getName());
		});
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
	
	private void addIfModuleEnabled(AnnotationAttributes attributes, String attrName, Runnable runnable) {
		boolean enableLive = attributes.getBoolean(attrName);
		if (enableLive) {
			runnable.run();
		}
	}

}
