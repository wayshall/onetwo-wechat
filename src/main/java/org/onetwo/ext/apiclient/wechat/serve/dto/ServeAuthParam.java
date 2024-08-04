package org.onetwo.ext.apiclient.wechat.serve.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper=true)
public class ServeAuthParam extends BaseServeParam {
	@NotBlank
	private String signature;
	@NotBlank
	private String echostr;
}
