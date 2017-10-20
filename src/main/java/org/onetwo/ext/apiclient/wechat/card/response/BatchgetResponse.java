package org.onetwo.ext.apiclient.wechat.card.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class BatchgetResponse extends WechatResponse {
	
	@JsonProperty("card_id_list")
	List<String> cardIdList;
	int total_num;

}
