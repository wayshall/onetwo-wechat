package org.onetwo.ext.apiclient.work.contact.api;

import java.util.List;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.work.contact.request.UpdateUserInfoRequest;
import org.onetwo.ext.apiclient.work.contact.response.Convert2OpenidResponse;
import org.onetwo.ext.apiclient.work.contact.response.Convert2OpenidResponse.Convert2UseridResponse;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserListResponse;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient(accessTokenType=AccessTokenTypes.CONTACTS)
public interface WorkUserClient {

	@GetMapping(path="/user/get")
	WorkUserInfoResponse getUser(AccessTokenInfo accessTokenInfo, String userid);
	
	/****
	 * https://work.weixin.qq.com/api/doc#90000/90135/90197
	 * 
	 * @author weishao zeng
	 * @param accessTokenInfo
	 * @param request
	 * @return
	 */
	@PostMapping(path="/user/update")
	WechatResponse update(AccessTokenInfo accessTokenInfo, @RequestBody UpdateUserInfoRequest request);
	
	/***
	 * https://work.weixin.qq.com/api/doc#90000/90135/90199
	 * 
	 * @author weishao zeng
	 * @param accessTokenInfo
	 * @param request
	 * @return
	 */
	@PostMapping(path="/user/batchdelete")
	WechatResponse batchdelete(AccessTokenInfo accessTokenInfo, @RequestBody BatchdeleteRequest request);
	
	/****
	 * https://work.weixin.qq.com/api/doc#90000/90135/90201
	 * @author weishao zeng
	 * @param accessTokenInfo
	 * @param departmentId
	 * @param fetchChild 1/0：是否递归获取子部门下面的成员
	 * @return
	 */
	@GetMapping(path="/user/list")
	WorkUserListResponse getList(AccessTokenInfo accessTokenInfo, 
								@RequestParam("department_id") Long departmentId, 
								@RequestParam("fetch_child") int fetchChild);
	

	/***
	 * userid转openid
	 * 
	 * https://work.weixin.qq.com/api/doc#90000/90135/90202
	 * @author weishao zeng
	 * @param accessTokenInfo
	 * @param userid
	 * @return
	 */
	@PostMapping(path="/user/convert_to_openid")
	Convert2OpenidResponse convert2Openid(AccessTokenInfo accessTokenInfo, @RequestBody Convert2OpenidRequest request);
	
	/***
	 * openid转userid
	 * 
	 * https://work.weixin.qq.com/api/doc#90000/90135/90202
	 * 
	 * @author weishao zeng
	 * @param accessTokenInfo
	 * @param openid
	 * @return
	 */
	@PostMapping(path="/user/convert_to_userid")
	Convert2UseridResponse convert2Userid(AccessTokenInfo accessTokenInfo, @RequestBody Convert2UseridRequest request);
	
	@Data
	@Builder
	@AllArgsConstructor
	public class Convert2OpenidRequest {
		String userid;
	}

	@Builder
	@AllArgsConstructor
	@Data
	public class Convert2UseridRequest {
		String openid;
	}

	@Builder
	@AllArgsConstructor
	@Data
	public class BatchdeleteRequest {
		List<String> useridlist;
	}
}

