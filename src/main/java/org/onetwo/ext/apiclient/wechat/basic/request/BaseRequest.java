package org.onetwo.ext.apiclient.wechat.basic.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.onetwo.common.utils.FieldName;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName="baseRequest")
public class BaseRequest {
	@FieldName("access_token")
	private String accessToken;

}
