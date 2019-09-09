package org.onetwo.ext.apiclient.qcloud.sms.service.impl;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.exception.ServiceException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.qcloud.sms.SmsException;
import org.onetwo.ext.apiclient.qcloud.sms.SmsProperties;
import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.onetwo.ext.apiclient.qcloud.util.QCloudErrors.SmsErrors;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import lombok.Getter;

/**
 * https://cloud.tencent.com/document/product/382/3771#.E7.9F.AD.E4.BF.A1.E5.8F.91.E9.80.81.E9.94.99.E8.AF.AF.E7.A0.81
 * @author weishao zeng
 * <br/>
 */
public class QCloudSmsService implements InitializingBean, SmsService {
	
	protected final Logger logger = JFishLoggerFactory.getLogger(getClass());
	
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
		
		String phoneNumber = request.getPhoneNumber();
		if (LangUtils.isNotEmpty(smsProperties.getWhiteList())) {
			// 白名单不为空，执行白名单规则
			if (!smsProperties.getWhiteList().contains(phoneNumber)) {
				if (logger.isWarnEnabled()) {
					logger.warn("the phone number[{}] is not in white list, ignore send message!", phoneNumber);
				}
				return ;
			}
		}else if (isInBlackList(phoneNumber)) {
			if (logger.isWarnEnabled()) {
				logger.warn("the phone number[{}] is in black list, ignore send message!", phoneNumber);
			}
			return ;
		}
		
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
    		result = this.smsSingleSender.sendWithParam(request.getNationCode(), phoneNumber, request.getTemplId(), params, request.getSign(), request.getExtend(), request.getExt());
		} catch (Exception e) {
			throw new SmsException(SmsErrors.ERR_SMS_SEND, e);
		}
    	if(result.result!=0) {
			JFishLoggerFactory.findMailLogger().error("send sms error, mobile: {},  templateId: {}, error: {}", phoneNumber, request.getTemplId(), result);
    		throw new SmsException(result.errMsg, String.valueOf(result.result));
    	}
    }
	
	private boolean isInBlackList(String phoneNumber) {
		if (LangUtils.isEmpty(smsProperties.getBlackList())) {
			return false;
		}
		return smsProperties.getBlackList().contains(phoneNumber);
	}
	
}

