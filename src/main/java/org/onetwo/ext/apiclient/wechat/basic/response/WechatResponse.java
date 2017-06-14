package org.onetwo.ext.apiclient.wechat.basic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wayshall
 * <br/>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatResponse {
	
	private Integer errcode;
	private String errmsg;
	
	public boolean isSuccess(){
		return errcode==null || errcode.intValue()==0;
	}

}
