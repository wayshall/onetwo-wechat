package org.onetwo.ext.apiclient.wechat.serve.msg;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class EncryptMsg {
	
	private String toUserName;
	private String encrypt;

}
