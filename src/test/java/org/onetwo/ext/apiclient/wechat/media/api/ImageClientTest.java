package org.onetwo.ext.apiclient.wechat.media.api;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.media.response.UploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author wayshall
 * <br/>
 */
public class ImageClientTest extends WechatBaseTestsAdapter {
	@Autowired
	private ImageClient imageClient;
	@Autowired
	private AccessTokenService accessTokenService;
	
	@Test
	public void testUpload(){
		Resource buffer = new ClassPathResource("img/kq.jpg");
		assertThatExceptionOfType(ApiClientException.class)
		.isThrownBy(()->{
			UploadResponse res = this.imageClient.uploadimg(accessTokenInfo, buffer);
			System.out.println("url: " + res.getUrl());
		})
		.withMessage("api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限");
	}

}
