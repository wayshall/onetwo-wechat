package org.onetwo.ext.apiclient.wechat.card.api;

import java.util.Arrays;

import org.junit.Test;
import org.onetwo.common.jackson.JsonMapper;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.card.request.BatchgetRequest;
import org.onetwo.ext.apiclient.wechat.card.response.BatchgetResponse;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.CardStatus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class CardClientTest extends WechatBaseTestsAdapter {
	
	@Autowired
	CardClient cardClient;
	
	@Test
	public void testBatchget(){
		BatchgetRequest request = BatchgetRequest.builder()
												 .statusList(Arrays.asList(CardStatus.CARD_STATUS_NOT_VERIFY, 
														 					CardStatus.CARD_STATUS_DISPATCH))
												 .build();
		String json = JsonMapper.IGNORE_NULL.toJson(request);
		System.out.println("json:"+json);
		BatchgetResponse res = cardClient.batchget(request);
		System.out.println("res:" + res);
	}

}
