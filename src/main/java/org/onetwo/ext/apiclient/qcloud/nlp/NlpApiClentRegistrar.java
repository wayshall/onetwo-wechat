package org.onetwo.ext.apiclient.qcloud.nlp;

import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.simple.SimpleApiClientResponseHandler;
import org.onetwo.common.apiclient.simple.SimpleApiClentRegistrar;
import org.onetwo.ext.apiclient.qcloud.nlp.api.NlpApiV2;

/**
 * @author weishao zeng
 * <br/>
 */
public class NlpApiClentRegistrar extends SimpleApiClentRegistrar {

	public NlpApiClentRegistrar() {
		super(NlpApiV2.class);
		SimpleApiClientResponseHandler<ApiClientMethod> handler = new SimpleApiClientResponseHandler<>();
		setResponseHandler(handler);
	}

}
