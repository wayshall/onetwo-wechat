package org.onetwo.ext.apiclient.qcloud.nlp;

import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.simple.SimpleApiClientResponseHandler;
import org.onetwo.common.apiclient.simple.WithoutImportingAnnotationApiClentRegistrar;
import org.onetwo.ext.apiclient.qcloud.nlp.api.NlpApiV2;
import org.onetwo.ext.apiclient.qcloud.nlp.response.NlpV2BaseResponse;

/**
 * @author weishao zeng
 * <br/>
 */
public class NlpApiClentRegistrar extends WithoutImportingAnnotationApiClentRegistrar {

	public NlpApiClentRegistrar() {
		super(NlpApiV2.class);
		SimpleApiClientResponseHandler<ApiClientMethod, NlpV2BaseResponse> handler = new SimpleApiClientResponseHandler<>();
		setResponseHandler(handler);
	}

}
