package org.onetwo.ext.apiclient.qcloud.sms.service.impl;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.exception.ServiceException;
import org.onetwo.ext.apiclient.qcloud.sms.SmsProperties;
import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.onetwo.ext.apiclient.qcloud.util.QCloudErrors.SmsErrors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import lombok.Getter;

/**
 * @author weishao zeng
 * <br/>
 */
public class QCloudSmsService implements InitializingBean, SmsService {
	
	private SmsSingleSender smsSingleSender;
	@Getter
	private SmsProperties smsProperties;
	
	public QCloudSmsService(SmsProperties smsProperties) {
		super();
		this.smsProperties = smsProperties;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(smsProperties, "qcloud.sms properties is missing...");
		this.smsSingleSender = new SmsSingleSender(smsProperties.getAppId(), smsProperties.getAppKey());
	}
	

	protected void checkRequest(SendSmsRequest request) {
		if (StringUtils.isBlank(request.getPhoneNumber()) || request.getPhoneNumber().length()!=11) {
			throw new ServiceException(SmsErrors.ERR_MOBILE_LENTH);
		}
	}

	/* (non-Javadoc)
	 * @see org.onetwo.ext.apiclient.qcloud.sms.service.SmsService#sendTemplateMessage(org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest)
	 */
	@Override
	public void sendTemplateMessage(SendSmsRequest request) {
		this.checkRequest(request);
		ArrayList<String> params = null;
		if (request.getParams()!=null ) {
			if (request.getParams() instanceof ArrayList) {
				params = (ArrayList<String>)request.getParams();
			} else {
				params = new ArrayList<>(request.getParams());
			}
		}
		SmsSingleSenderResult result = null;
    	try {
    		result = this.smsSingleSender.sendWithParam(request.getNationCode(), request.getPhoneNumber(), request.getTemplId(), params, request.getSign(), request.getExtend(), request.getExt());
		} catch (Exception e) {
			throw new ServiceException(SmsErrors.ERR_SMS_SEND, e);
		}
    	if(result.result!=0) {
    		throw new ServiceException(result.errMsg, SmsErrors.ERR_SMS_SEND.name()).put("result", result.result);
    	}
    }
	
}

