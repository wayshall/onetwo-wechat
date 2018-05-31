package org.onetwo.ext.apiclient.wechat.media.api;

import org.junit.Test;
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
	AccessTokenService accessTokenService;
	
	@Test
	public void test(){
		String token = accessTokenService.getAccessToken().getAccessToken();
		Resource buffer = new ClassPathResource("img/kq.jpg");
		UploadResponse res = this.imageClient.upload(token, buffer);
		System.out.println("url: " + res.getUrl());
	}

}
