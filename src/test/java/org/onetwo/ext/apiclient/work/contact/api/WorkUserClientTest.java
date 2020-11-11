package org.onetwo.ext.apiclient.work.contact.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.spring.copier.CopyUtils;
import org.onetwo.ext.apiclient.work.WorkWechatBaseBootTests;
import org.onetwo.ext.apiclient.work.contact.api.WorkUserClient.Convert2OpenidRequest;
import org.onetwo.ext.apiclient.work.contact.api.WorkUserClient.Convert2UseridRequest;
import org.onetwo.ext.apiclient.work.contact.request.UpdateUserInfoRequest;
import org.onetwo.ext.apiclient.work.contact.response.Convert2OpenidResponse;
import org.onetwo.ext.apiclient.work.contact.response.Convert2OpenidResponse.Convert2UseridResponse;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class WorkUserClientTest extends WorkWechatBaseBootTests {
	
	@Autowired
	WorkUserClient workUserClient;
	
	@Test
	public void testGetUser() {
		WorkUserInfoResponse res = workUserClient.getUser(getContactAccessToken(), "ZengWeiShao");
		System.out.println("WorkUserInfoResponse :" + res);
		assertThatExceptionOfType(ApiClientException.class).isThrownBy(() -> {
			WorkUserInfoResponse res2 = workUserClient.getUser(getContactAccessToken(), "");
			System.out.println("res2:" + res2);
		})
		.withMessageContaining("用户不存在");
	}
	
	@Test
	public void testUpdateUser() {
		String userid = "ZengWeiShao";
		WorkUserInfoResponse res = workUserClient.getUser(getContactAccessToken(), userid);
		System.out.println("res:" + res);
		
		UpdateUserInfoRequest update = CopyUtils.copy(UpdateUserInfoRequest.class, res);
		update.setAlias("曾卫韶");
		workUserClient.update(getContactAccessToken(), update);
		
		WorkUserInfoResponse res2 = workUserClient.getUser(getContactAccessToken(), userid);
		System.out.println("res2:" + res2);
		assertThat(res2.getAlias()).isEqualTo(update.getAlias());
	}
	
	@Test
	public void testConvert2Openid() {
		String userid = "qy0106ea30ee79ba0029a316acb3";
		Convert2OpenidResponse res = workUserClient.convert2Openid(getContactAccessToken(), 
																	Convert2OpenidRequest.builder()
																						.userid(userid)
																						.build());
		System.out.println("res: " + res); //
		assertThat(res.isSuccess()).isTrue();
		assertThat(res.getOpenid()).isNotEmpty();
	}

	@Test
	public void testConvert2Userid() {
		String openid = "otiEv1azWn4ifrrWz5-82Xj3Wgnc";//"otiEv1dIk_wm25KX37rEl614d2_E";
		Convert2UseridResponse res = workUserClient.convert2Userid(getContactAccessToken(), 
																	Convert2UseridRequest.builder()
																						.openid(openid)
																						.build());
		System.out.println("res: " + res);
		assertThat(res.isSuccess()).isTrue();
		assertThat(res.getUserid()).isNotEmpty();
	}
}

