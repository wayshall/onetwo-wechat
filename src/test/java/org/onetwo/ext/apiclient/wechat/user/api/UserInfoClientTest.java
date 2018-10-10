package org.onetwo.ext.apiclient.wechat.user.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.user.api.UserInfoClient.UserInfoBatchGetRequest;
import org.onetwo.ext.apiclient.wechat.user.api.UserInfoClient.UserInfoBatchGetResponse;
import org.onetwo.ext.apiclient.wechat.user.request.UserInfoGetRequest;
import org.onetwo.ext.apiclient.wechat.user.response.UserInfoGetResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class UserInfoClientTest extends WechatBaseTestsAdapter {

	@Autowired
	UserInfoClient userInfoClient;

	@Test
	public void testGet() {
		UserInfoGetRequest request = UserInfoGetRequest.builder()
														.openid("oA0wB1aG_lOGUmRBClYMJv9hrIYI")
														.build();
		UserInfoGetResponse response = this.userInfoClient.get(accessTokenInfo, request);
		System.out.println("res: " + response);
		assertThat(response.isSuccess()).isTrue();
		
		UserInfoBatchGetRequest batchRequest = new UserInfoBatchGetRequest().add(request);
		UserInfoBatchGetResponse batchRes = this.userInfoClient.batchget(accessTokenInfo, batchRequest);
		System.out.println("batch res: " + batchRes);
		assertThat(batchRes.isSuccess()).isTrue();
	}
}
