package org.onetwo.ext.apiclient.wechat.wxa.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * @author wayshall
 * <br/>
 */
public class ContentSecurityClientTest extends WechatBaseTestsAdapter {
	@Autowired
	private ContentSecurityClient contentSecurityClient;
	
	@Test
	public void testImgSecCheck() throws Exception{
		AccessTokenInfo accessToken = getAccessToken();
		System.out.println("tokenInfo:"+accessToken);
		Resource media = new FileSystemResource("d:/test/test.jpg");
//		media = new ClassPathResource("img/kq.jpg");
//		media = new ByteArrayHttpMessageConverter();
//		byte[] data = FileUtils.toByteArray(media.getInputStream());
		WechatResponse res = contentSecurityClient.imgSecCheck(accessToken, media);
		System.out.println("res:" + res);
		assertThat(res.isSuccess()).isTrue();
	}

}
