package org.onetwo.ext.apiclient.qcloud.aiart.service;

import org.onetwo.ext.apiclient.qcloud.aiart.vo.AiartTxt2ImageRequest;
import org.onetwo.ext.apiclient.qcloud.aiart.vo.AiartTxt2ImageResponse;

public interface AiartService {

	AiartTxt2ImageResponse textToImage(AiartTxt2ImageRequest request);

}