package org.onetwo.ext.apiclient.work.contact.api;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.work.WorkWechatBaseBootTests;
import org.onetwo.ext.apiclient.work.contact.response.DepartmentListResponse;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse;
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

}

