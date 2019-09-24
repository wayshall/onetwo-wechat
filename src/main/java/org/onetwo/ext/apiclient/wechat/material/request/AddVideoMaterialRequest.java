package org.onetwo.ext.apiclient.wechat.material.request;

import lombok.Data;

import org.onetwo.common.apiclient.ApiArgumentTransformer;
import org.onetwo.common.jackson.JsonMapper;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class AddVideoMaterialRequest implements ApiArgumentTransformer {
	private String title;
	private String introduction;
	
	@Override
	public Object asApiValue() {
		String json = JsonMapper.IGNORE_NULL.toJson(this);
		return json;
	}
	
}
