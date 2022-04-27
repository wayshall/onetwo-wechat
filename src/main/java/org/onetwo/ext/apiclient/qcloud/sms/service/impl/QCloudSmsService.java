package org.onetwo.ext.apiclient.qcloud.sms.service.impl;

import java.util.ArrayList;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.qcloud.sms.SmsException;
import org.onetwo.ext.apiclient.qcloud.sms.SmsProperties;
import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.onetwo.ext.apiclient.qcloud.util.QCloudErrors.SmsErrors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

/**
 * https://cloud.tencent.com/document/product/382/3771#.E7.9F.AD.E4.BF.A1.E5.8F.91.E9.80.81.E9.94.99.E8.AF.AF.E7.A0.81
 * @author weishao zeng
 * @deprecated use TencentSdkSmsService instead of
 * <br/>
 */
public class QCloudSmsService extends BaseSmsService implements InitializingBean, SmsService {
	
	
	private SmsSingleSender smsSingleSender;
	
	public QCloudSmsService(SmsProperties smsProperties) {
		super(smsProperties);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(smsProperties, "qcloud.sms properties is missing...");
		this.smsSingleSender = new SmsSingleSender(smsProperties.getAppId(), smsProperties.getAppKey());
	}
	

	/* (non-Javadoc)
	 * @see org.onetwo.ext.apiclient.qcloud.sms.service.SmsService#sendTemplateMessage(org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest)
	 */
	@Override
	public void sendTemplateMessage(SendSmsRequest request) {
		this.checkRequest(request);
		
		if (!LangUtils.isEmpty(request.getPhoneNumbers())) {
			throw new SmsException(SmsErrors.ERR_NOT_SUPPORT_BATCH);
		}
		
		String phoneNumber = request.getPhoneNumber();
		if (!checkWhiteBlackList(phoneNumber)) {
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
	
}

