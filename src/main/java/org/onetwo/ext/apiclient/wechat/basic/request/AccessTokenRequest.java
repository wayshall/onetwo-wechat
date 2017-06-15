package org.onetwo.ext.apiclient.wechat.basic.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.onetwo.common.utils.FieldName;

/**
 * 如果AccessTokenRequest没有设置过accessToken，则自动设置
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName="accessTokenRequest")
public class AccessTokenRequest {
	@FieldName("access_token")
	private String accessToken;

}
