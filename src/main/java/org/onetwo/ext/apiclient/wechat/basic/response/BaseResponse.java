package org.onetwo.ext.apiclient.wechat.basic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wayshall
 * <br/>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
	
	private Integer errcode;
	private String errmsg;
	
	public boolean isSuccess(){
		return errcode==null || errcode.intValue()==0;
	}

}
