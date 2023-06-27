package org.onetwo.ext.apiclient.baidu.request;

import org.onetwo.common.apiclient.ApiClientMethodConfig;
import org.onetwo.common.apiclient.ApiClientMethodConfigProvider;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@NoArgsConstructor
public class BaiduBaseRequest implements ApiClientMethodConfigProvider {
	ApiClientMethodConfig apiConfig;

	@Override
	public ApiClientMethodConfig toApiClientMethodConfig() {
		return apiConfig;
	}
	
}
