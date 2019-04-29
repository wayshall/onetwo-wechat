package org.onetwo.ext.apiclient.work.contact.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.work.WorkWechatBaseBootTests;
import org.onetwo.ext.apiclient.work.contact.api.WorkUserClient.Convert2OpenidRequest;
import org.onetwo.ext.apiclient.work.contact.api.WorkUserClient.Convert2UseridRequest;
import org.onetwo.ext.apiclient.work.contact.response.Convert2OpenidResponse;
import org.onetwo.ext.apiclient.work.contact.response.Convert2OpenidResponse.Convert2UseridResponse;
import org.onetwo.ext.apiclient.work.contact.response.DepartmentListResponse;
import org.onetwo.ext.apiclient.work.contact.response.DepartmentListResponse.DepartmentData;
import org.onetwo.ext.apiclient.work.contact.response.TagListResponse;
import org.onetwo.ext.apiclient.work.contact.response.TagMemberResponse;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserListResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class ContractClientTest extends WorkWechatBaseBootTests {
	
	@Autowired
	WorkUserClient workUserClient;
	@Autowired
	WorkDepartmentClient workDepartmentClient;
	@Autowired
	WorkTagClient workTagClient;
	
	@Test
	public void testGetUser() {
		assertThatExceptionOfType(ApiClientException.class).isThrownBy(() -> {
			WorkUserInfoResponse res = workUserClient.getUser(getAccessToken(), "");
			System.out.println("res:" + res);
		})
		.withMessageContaining("用户不存在");
	}
	
	@Test
	public void testGetDepartment() {
		assertThatExceptionOfType(ApiClientException.class).isThrownBy(() -> {
			DepartmentListResponse res = this.workDepartmentClient.getList(getAccessToken(), 111111L);
			System.out.println("res: " + res);
		})
		.withMessageContaining("无效的部门id");
	}
	
	@Test
	public void testGetUserList() {
		DepartmentListResponse departRes = this.workDepartmentClient.getList(getAccessToken(), null);
		System.out.println("depart: " + departRes);
		assertThat(departRes.getDepartment()).isNotEmpty();
		DepartmentData depart = departRes.getDepartment().get(0);
		WorkUserListResponse userList = this.workUserClient.getList(getAccessToken(), depart.getId(), 0);
		System.out.println("userList: " + userList);
		assertThat(userList.getUserlist()).isNotEmpty();
	}
	
	@Test
	public void testTagGetList() {
		TagListResponse res = this.workTagClient.getList(getAccessToken());
		System.out.println("res: " + res);
		assertThat(res).isNotNull();
		assertThat(res.getTaglist()).isNotEmpty();
		
		Long tagid = res.getTaglist().get(0).getTagid();
		TagMemberResponse tagMembers = this.workTagClient.get(getAccessToken(), tagid);
		System.out.println("tagMembers: " + tagMembers);
		assertThat(tagMembers.getUserlist()).isNotEmpty();
		
	}
	
	@Test
	public void testConvert2Openid() {
		String userid = "qy0106ea30ee79ba0029a316acb3";
		Convert2OpenidResponse res = workUserClient.convert2Openid(getAccessToken(), 
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
		Convert2UseridResponse res = workUserClient.convert2Userid(getAccessToken(), 
																	Convert2UseridRequest.builder()
																						.openid(openid)
																						.build());
		System.out.println("res: " + res);
		assertThat(res.isSuccess()).isTrue();
		assertThat(res.getUserid()).isNotEmpty();
	}
}

