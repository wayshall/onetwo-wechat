package org.onetwo.ext.apiclient.wechat.serve.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.onetwo.common.spring.copier.ConvertToCamelProperty;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConvertToCamelProperty
public class MessageParam extends BaseServeParam {
	
	private String encryptType;
	private String msgSignature;
	
	public boolean isEncryptByAes(){
		return WechatConstants.ENCRYPT_TYPE_AES.equalsIgnoreCase(getEncryptType());
	}

}
