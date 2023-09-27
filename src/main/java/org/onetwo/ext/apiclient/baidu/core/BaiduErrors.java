package org.onetwo.ext.apiclient.baidu.core;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.onetwo.common.exception.ErrorType;

import com.google.common.collect.Maps;

/**
 * 错误码文档：
 * 
 * @author wayshall
 * <br/>
 */
public enum BaiduErrors implements ErrorType {
	ACCESS_TOKEN_EXPIRED("18", "access_token过期或错误,请刷新access_token或者重新授权"),
	;

	private static final Map<String	, BaiduErrors> ERROR_MAP;
	
	static {
		Map<String, BaiduErrors> temp = Maps.newHashMapWithExpectedSize(BaiduErrors.values().length);
		for(BaiduErrors err : BaiduErrors.values()){
			temp.put(err.errcode, err);
		}
		ERROR_MAP = Collections.unmodifiableMap(temp);
	}
	
	private String errcode;
	private String errmsg;

	private BaiduErrors(String errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	@Override
	public String getErrorCode() {
		return errcode.toString();
	}

	@Override
	public String getErrorMessage() {
		return errmsg;
	}
	
	public static Optional<BaiduErrors> byErrcode(String errcode){
		return Optional.ofNullable(ERROR_MAP.get(errcode));
	}
}
