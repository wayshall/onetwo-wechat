package org.onetwo.ext.apiclient.yly.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PrinterStateResponse extends YlyResponse {
	StateBody body;
	
	@Data
	static public class StateBody {
		/***
		 * 0离线 1在线 2缺纸
		 */
		int state;
	}

}
