package org.onetwo.ext.apiclient.work.contact.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.work.WorkWechatBaseBootTests;
import org.onetwo.ext.apiclient.work.contact.response.DepartmentListResponse;
import org.onetwo.ext.apiclient.work.contact.response.TagListResponse;
import org.onetwo.ext.apiclient.work.contact.response.TagMemberResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class ContractClientTest extends WorkWechatBaseBootTests {
	
	@Autowired
	WorkDepartmentClient workDepartmentClient;
	@Autowired
	WorkTagClient workTagClient;
	
	@Test
	public void testGetDepartment() {
		assertThatExceptionOfType(ApiClientException.class).isThrownBy(() -> {
			DepartmentListResponse res = this.workDepartmentClient.getList(getAccessToken(), 111111L);
			System.out.println("res: " + res);
		})
		.withMessageContaining("无效的部门id");
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
}

