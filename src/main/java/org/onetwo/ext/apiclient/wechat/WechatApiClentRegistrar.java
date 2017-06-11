package org.onetwo.ext.apiclient.wechat;

import java.lang.annotation.Annotation;

import org.onetwo.ext.apiclient.core.AbstractApiClentRegistrar;

/**
 * @author wayshall
 * <br/>
 */
public class WechatApiClentRegistrar extends AbstractApiClentRegistrar {

	@Override
	protected Class<? extends Annotation> getImportingAnnotationClass() {
		return EnableWechatClient.class;
	}

	@Override
	protected Class<? extends Annotation> getApiClientTagAnnotationClass() {
		return WechatApiClient.class;
	}

}
