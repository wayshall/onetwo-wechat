package org.onetwo.ext.apiclient.wechat.serve.dto;

import javax.servlet.http.HttpServletRequest;

import org.onetwo.common.web.utils.WebHolder;

import lombok.Builder;
import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class RequestHoder {
	
	public static RequestHoder current(){
		return builder().request(WebHolder.getRequest().get()).build();
	}
	HttpServletRequest request;
	
	@Builder
	public RequestHoder(HttpServletRequest request) {
		super();
		this.request = request;
	}

}
