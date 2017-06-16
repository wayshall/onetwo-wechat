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
public class MessageParam extends BaseServeParam {
	
	private String encrypt_type;
	private String msg_signature;

}
