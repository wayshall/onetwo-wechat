package org.onetwo.ext.apiclient.work.contact.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserListResponse;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface WorkUserClient {

	@GetMapping(path="/user/get")
	WorkUserInfoResponse getUser(AccessTokenInfo accessTokenInfo, String userid);
	
	/****
	 * https://work.weixin.qq.com/api/doc#90000/90135/90201
	 * @author weishao zeng
	 * @param accessTokenInfo
	 * @param departmentId
	 * @param fetchChild 1/0：是否递归获取子部门下面的成员
	 * @return
	 */
	@GetMapping(path="/user/list")
	WorkUserListResponse getList(AccessTokenInfo accessTokenInfo, @RequestParam("department_id") Long departmentId, @RequestParam("fetch_child") int fetchChild);
	
}

