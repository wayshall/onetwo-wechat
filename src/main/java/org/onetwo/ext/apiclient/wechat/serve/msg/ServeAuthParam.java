package org.onetwo.ext.apiclient.wechat.serve.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ServeAuthParam extends BaseServeParam {
	@NotBlank
	private String echostr;
}
