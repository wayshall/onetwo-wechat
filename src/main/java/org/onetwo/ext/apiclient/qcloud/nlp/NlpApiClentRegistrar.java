package org.onetwo.ext.apiclient.qcloud.nlp;

import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.simple.SimpleApiClientResponseHandler;
import org.onetwo.common.apiclient.simple.WithoutImportingAnnotationApiClentRegistrar;
import org.onetwo.ext.apiclient.qcloud.nlp.api.NlpApi;
import org.onetwo.ext.apiclient.qcloud.nlp.response.NlpBaseResponse;

/**
 * @author weishao zeng
 * <br/>
 */
public class NlpApiClentRegistrar extends WithoutImportingAnnotationApiClentRegistrar {

	public NlpApiClentRegistrar() {
		super(NlpApi.class);
		SimpleApiClientResponseHandler<ApiClientMethod, NlpBaseResponse> handler = new SimpleApiClientResponseHandler<>();
		setResponseHandler(handler);
	}

}
