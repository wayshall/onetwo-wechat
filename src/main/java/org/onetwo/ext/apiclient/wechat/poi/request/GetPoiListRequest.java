package org.onetwo.ext.apiclient.wechat.poi.request;

import lombok.Builder;
import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@Data
@Builder
public class GetPoiListRequest {
	
	int begin = 0;
	int limit = 10;

}
