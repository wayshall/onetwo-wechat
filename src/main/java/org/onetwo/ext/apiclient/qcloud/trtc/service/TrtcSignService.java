package org.onetwo.ext.apiclient.qcloud.trtc.service;
/**
 * @author weishao zeng
 * <br/>
 */

import org.onetwo.common.utils.Assert;
import org.onetwo.ext.apiclient.qcloud.trtc.TrtcProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.tencentyun.TLSSigAPIv2;

/****
 * https://cloud.tencent.com/document/product/647/17275
 * 
 * @author way
 *
 */
public class TrtcSignService implements InitializingBean {
	
	@Autowired
	private TrtcProperties trtcProperties;
	private TLSSigAPIv2 tlsSigApi;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(trtcProperties.getSdkAppId(), "sdkAppId must not be null");
		Assert.hasText(trtcProperties.getSecretKey(), "secretKey must not be blank");
		tlsSigApi = new TLSSigAPIv2(trtcProperties.getSdkAppId(), trtcProperties.getSecretKey());
	}
	
	/***
	 * 
	 * @author weishao zeng
	 * @param userId 
	 * @param expireInSeconds 过期时间，单位：秒
	 */
	public String genSig(String userId, Long expireInSeconds) {
		return this.tlsSigApi.genSig(userId, expireInSeconds!=null?expireInSeconds:trtcProperties.getDefaultExpireTime());
	}
	
}
