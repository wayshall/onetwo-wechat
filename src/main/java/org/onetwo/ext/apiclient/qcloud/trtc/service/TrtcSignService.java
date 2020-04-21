package org.onetwo.ext.apiclient.qcloud.trtc.service;
/**
 * @author weishao zeng
 * <br/>
 */

import org.apache.commons.lang3.StringUtils;
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
	
	/***
	 * 复制自微信小程序sdk客户端，保持一致
	 * @author weishao zeng
	 * @param streamUrl
	 * @return
	 */
	public static String getStreamIDByStreamUrl(String streamUrl) {
	    if (StringUtils.isBlank(streamUrl)) {
	        return null;
	    }
	    //推流地址格式: rtmp://8888.livepush.myqcloud.com/path/8888_test_12345?txSecret=aaa&txTime=bbb
	    //拉流地址格式: rtmp://8888.livepush.myqcloud.com/path/8888_test_12345
	    //             http://8888.livepush.myqcloud.com/path/8888_test_12345.flv
	    //             http://8888.livepush.myqcloud.com/path/8888_test_12345.m3u8

	    String subStr = streamUrl;
	    int index = subStr.indexOf('?');
	    if (index >= 0) {
	        subStr = subStr.substring(0, index);
	    }
	    if (StringUtils.isBlank(subStr)) {
	        return null;
	    }
	    index = subStr.lastIndexOf('/');
	    if (index >= 0) {
	        subStr = subStr.substring(index + 1);
	    }
	    if (StringUtils.isBlank(subStr)) {
	        return null;
	    }
	    index = subStr.indexOf('.');
	    if (index >= 0) {
	        subStr = subStr.substring(0, index);
	    }
	    if (StringUtils.isBlank(subStr)) {
	        return null;
	    }
	    return subStr;
	}
	
}
