package org.onetwo.ext.apiclient.qcloud.sms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.exception.ServiceException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.qcloud.sms.SmsException;
import org.onetwo.ext.apiclient.qcloud.sms.SmsProperties;
import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.onetwo.ext.apiclient.qcloud.util.QCloudErrors.SmsErrors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;

/**
 * https://cloud.tencent.com/document/product/382/43194#.E5.8F.91.E9.80.81.E7.9F.AD.E4.BF.A1
 * 
 * @author weishao zeng
 * <br/>
 */
@Primary
public class TencentSdkSmsService extends BaseSmsService implements InitializingBean, SmsService {
	
	private SmsClient smsClient;
	
	public TencentSdkSmsService(SmsProperties smsProperties) {
		super(smsProperties);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(smsProperties, "qcloud.sms properties is missing...");
		String region = smsProperties.getCloudProperties().getRegion();
		this.smsClient = new SmsClient(smsProperties.getCloudProperties().newCredential(), region, smsProperties.newClientProfile());
	}
	

	protected void checkRequest(SendSmsRequest request) {
		if (StringUtils.isBlank(request.getPhoneNumber()) || request.getPhoneNumber().length()<11) {
			throw new ServiceException(SmsErrors.ERR_MOBILE_LENTH);
		}
	}

	/* (non-Javadoc)
	 * @see org.onetwo.ext.apiclient.qcloud.sms.service.SmsService#sendTemplateMessage(org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest)
	 */
	@Override
	public void sendTemplateMessage(SendSmsRequest request) {
		List<String> phoneNumbers = null;
		if (!LangUtils.isEmpty(request.getPhoneNumbers())) {
			phoneNumbers = new ArrayList<>(request.getPhoneNumbers().length);
			for (String phoneNumber : request.getPhoneNumbers()) {
				if (!checkWhiteBlackList(phoneNumber)) {
					continue;
				}
				phoneNumbers.add(fixPhoneNumber(phoneNumber));
			}
		} else if (StringUtils.isNotBlank(request.getPhoneNumber())) {
			String phoneNumber = request.getPhoneNumber();
			if (!checkWhiteBlackList(phoneNumber)) {
				throw new SmsException(SmsErrors.ERR_NOT_IN_WHITE_BLACK_LIST);
			}
			phoneNumbers = Lists.newArrayList(fixPhoneNumber(phoneNumber));
		} else {
			throw new SmsException(SmsErrors.ERR_MOBILE_LENTH);
		}
		
		ArrayList<String> params = null;
		if (request.getParams()!=null ) {
			if (request.getParams() instanceof ArrayList) {
				params = (ArrayList<String>)request.getParams();
			} else {
				params = new ArrayList<>(request.getParams());
			}
		}
//		SmsSingleSenderResult result = null;
		SendSmsResponse result = null;
    	try {
//    		result = this.smsSingleSender.sendWithParam(request.getNationCode(), phoneNumber, request.getTemplId(), params, request.getSign(), request.getExtend(), request.getExt());
    		com.tencentcloudapi.sms.v20190711.models.SendSmsRequest req = new com.tencentcloudapi.sms.v20190711.models.SendSmsRequest();
    		req.setSmsSdkAppid(String.valueOf(smsProperties.getAppId()));
    		/* 用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
//            req.setSessionContext(session);
    		req.setTemplateID(String.valueOf(request.getTemplId()));
    		req.setTemplateParamSet(params.toArray(new String[0]));
    		req.setPhoneNumberSet(phoneNumbers.toArray(new String[0]));
    		req.setSign(request.getSign());
            
    		result = this.smsClient.SendSms(req);
    		logger.info("send result: {}", SendSmsResponse.toJsonString(result));
		} catch (Exception e) {
			throw new SmsException(SmsErrors.ERR_SMS_SEND, e);
		}
    	if(!LangUtils.isEmpty(result.getSendStatusSet())) {
    		SendStatus sendStatus = result.getSendStatusSet()[0];
    		if (!isSendOK(sendStatus)) {
    			JFishLoggerFactory.findMailLogger().error("send sms error, mobile: {},  templateId: {}, error: {}", phoneNumbers, request.getTemplId(), result);
        		throw new SmsException(sendStatus.getMessage(), String.valueOf(sendStatus.getCode()));
    		}
    	}
    }
	
	private String fixPhoneNumber(String phoneNumber) {
		if (!phoneNumber.startsWith("+")) {
			// 这个api需要国家地区前缀，否则会抛错：UnsupportedOperation.ContainDomesticAndInternationalPhoneNumber
			// 默认自动加上中国地区前缀
			phoneNumber = "+86" + phoneNumber;
		}
		return phoneNumber;
	}
	
	private boolean isSendOK(SendStatus sendStatus) {
		return sendStatus.getCode().equalsIgnoreCase("OK");
	}
	
}

