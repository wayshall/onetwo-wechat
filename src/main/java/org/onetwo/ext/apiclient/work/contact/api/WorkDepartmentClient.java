package org.onetwo.ext.apiclient.work.contact.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.work.contact.response.DepartmentListResponse;
import org.onetwo.ext.apiclient.work.contact.response.DepartmentListResponse.CreateDepartmentResponse;
import org.onetwo.ext.apiclient.work.contact.response.DepartmentListResponse.DepartmentData;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface WorkDepartmentClient {
	
	/***
	 * 文档：https://work.weixin.qq.com/api/doc#90000/90135/90208
	 * 
	 * @author weishao zeng
	 * @param accessTokenInfo
	 * @param id
	 * @return
	 */
	@GetMapping(path="/department/list")
	DepartmentListResponse getList(AccessTokenInfo accessTokenInfo, Long id);
	
	
	@GetMapping(path="/department/update")
	WechatResponse update(AccessTokenInfo accessTokenInfo, @RequestBody DepartmentData department);
	
	
	@GetMapping(path="/department/create")
	CreateDepartmentResponse create(AccessTokenInfo accessTokenInfo, @RequestBody DepartmentData department);
	
	@GetMapping(path="/department/delete")
	WechatResponse delete(AccessTokenInfo accessTokenInfo, Long id);

}

