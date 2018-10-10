package org.onetwo.ext.apiclient.wechat.user.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
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
														.openid("test")
														.build();
		UserInfoGetResponse response = this.userInfoClient.get(accessTokenInfo, request);
		assertThat(response.isSuccess()).isTrue();
	}
}
