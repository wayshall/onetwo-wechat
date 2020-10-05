package org.onetwo.ext.apiclient.qcloud.sms;

import java.util.ArrayList;
import java.util.List;

import org.onetwo.ext.apiclient.qcloud.QCloudProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@ConfigurationProperties(SmsProperties.KEY)
public class SmsProperties {
	public static final String KEY = "qcloud.sms";
	public static final String ENABLE_KEY = KEY + ".appKey";
	public static final String RETRYABLE_ENABLE = KEY + ".retryable";
	
	private int appId;
	private String appKey;
	
	/****
	 * 白名单，只允许对白名单里号码发送短信，如果白名单为空，则执行黑名单
	 */
	private List<String> whiteList = new ArrayList<String>();
	
	/***
	 * 黑名单，黑名单里的号码将不会发送信息
	 */
	private List<String> blackList = new ArrayList<String>();

	/*private String protection;
	private String protectionTime;
	*/
	
	/****
	 * https://cloud.tencent.com/document/product/382/43194#.E5.8F.91.E9.80.81.E7.9F.AD.E4.BF.A1
	 * 
	 * tencent sdk 发送需要依赖QCloudProperties
	 */
	@Autowired
	QCloudProperties cloudProperties;
	String endPoint = "sms.tencentcloudapi.com";
	String requestMethod = "POST";
	
	/****
	 * 默认一分钟内不能重发
	 * @author weishao zeng
	 * @return
	 *//*
	public long getProtectionTimeInSeconds() {
		return LangOps.timeToSeconds(protectionTime, 60L);
	}*/
	

	public ClientProfile newClientProfile() {
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(endPoint);
        httpProfile.setReqMethod(requestMethod);
//        httpProfile.setWriteTimeout(writeTimeout);
        
		ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(cloudProperties.getSignMethod());
        clientProfile.setHttpProfile(httpProfile);
        
        return clientProfile;
	}
}

