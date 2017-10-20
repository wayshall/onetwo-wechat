package org.onetwo.ext.apiclient.wechat.card.request;

import java.util.List;

import lombok.Builder;
import lombok.Data;

import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.CardStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */

@Data
public class BatchgetRequest {
	
	int offset;
	int count;
	@JsonProperty("status_list")
	List<CardStatus> statusList;
	
	@Builder
	public BatchgetRequest(int offset, int count, List<CardStatus> statusList) {
		super();
		this.offset = offset;
		this.count = count;
		if(this.count==0){
			this.count = 10;
		}
		this.statusList = statusList;
	}
	
}
