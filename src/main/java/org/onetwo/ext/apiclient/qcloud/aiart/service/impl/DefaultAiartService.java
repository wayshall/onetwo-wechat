package org.onetwo.ext.apiclient.qcloud.aiart.service.impl;

import org.onetwo.common.exception.ServiceException;
import org.onetwo.ext.apiclient.qcloud.aiart.AiartProperties;
import org.onetwo.ext.apiclient.qcloud.aiart.service.AiartService;
import org.onetwo.ext.apiclient.qcloud.aiart.vo.AiartTxt2ImageRequest;
import org.onetwo.ext.apiclient.qcloud.aiart.vo.AiartTxt2ImageResponse;
import org.onetwo.ext.apiclient.qcloud.auth.CredentialProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.tencentcloudapi.aiart.v20221229.AiartClient;
import com.tencentcloudapi.aiart.v20221229.models.TextToImageRequest;
import com.tencentcloudapi.aiart.v20221229.models.TextToImageResponse;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

public class DefaultAiartService implements InitializingBean, AiartService {

	@Autowired
	private AiartProperties properties;
	private AiartClient aiartClient;
	@Autowired
	private CredentialProvider credentialProvider;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(properties, "qcloud.aiart properties is missing...");
		this.aiartClient = new AiartClient(credentialProvider.getCredential(), credentialProvider.getRegion());
	}
	
	@Override
	public AiartTxt2ImageResponse textToImage(AiartTxt2ImageRequest request) {
		TextToImageRequest req = new TextToImageRequest();
		req.setPrompt(request.getPropmt());
		req.setNegativePrompt(request.getNegativePrompt());
		req.setRspImgType(request.getImgType());
		req.setStyles(new String[] {"401"});
		
		TextToImageResponse res;
		try {
			res = aiartClient.TextToImage(req);
		} catch (TencentCloudSDKException e) {
			throw new ServiceException("qcloud text to image error: " + e.getMessage(), e);
		}
		AiartTxt2ImageResponse response = new AiartTxt2ImageResponse();
		if (request.isUrlImage()) {
			response.setImageUrl(res.getResultImage());
		} else {
			response.setImageData(res.getResultImage());
		}
		return response;
	}
}
