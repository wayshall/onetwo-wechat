package org.onetwo.ext.apiclient.qcloud.sms.service;

import java.util.ArrayList;

import org.onetwo.common.exception.ServiceException;
import org.onetwo.ext.apiclient.qcloud.sms.SmsProperties;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.onetwo.ext.apiclient.qcloud.util.QCloudErrors.SmsErrors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

/**
 * @author weishao zeng
 * <br/>
 */
public class SmsService implements InitializingBean {
	
	private SmsSingleSender smsSingleSender;
	private SmsProperties smsProperties;
	
	public SmsService(SmsProperties smsProperties) {
		super();
		this.smsProperties = smsProperties;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(smsProperties, "qcloud.sms properties is missing...");
		this.smsSingleSender = new SmsSingleSender(smsProperties.getAppId(), smsProperties.getAppKey());
	}

	public void sendTemplateMessage(SendSmsRequest request) {
		ArrayList<String> params = null;
		if (request.getParams()!=null ) {
			if (request.getParams() instanceof ArrayList) {
				params = (ArrayList<String>)request.getParams();
			} else {
				params = new ArrayList<>(request.getParams());
			}
		}
		SmsSingleSenderResult result = null;
		String sign = request.getSign()==null?"":request.getSign();
		String extend = request.getExtend()==null?"":request.getExtend();
		String ext = request.getExt()==null?"":request.getExt();
    	try {
    		result = this.smsSingleSender.sendWithParam(request.getNationCode(), request.getPhoneNumber(), request.getTemplId(), params, sign, extend, ext);
		} catch (Exception e) {
			throw new ServiceException(SmsErrors.ERR_SMS_SEND, e);
		}
    	if(result.result!=0) {
    		throw new ServiceException(result.errMsg);
    	}
    }
	
}

