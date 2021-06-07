package org.onetwo.ext.apiclient.tt.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TTAuthRequest implements Serializable {

	@NotNull
	@NotBlank
	private String appid;
	
	@NotNull
	@NotBlank
	private String secret;
	
	private String code;
	
	@JsonProperty("anonymous_code")
	private String anonymousCode;
	
	
}
