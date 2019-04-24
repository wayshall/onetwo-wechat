package org.onetwo.ext.apiclient.wechat.wxa.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Test;
import org.onetwo.common.apiclient.resouce.FileNameByteArrayResource;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.file.FileUtils;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author wayshall
 * <br/>
 */
@ActiveProfiles("product")
public class ContentSecurityClientTest extends WechatBaseTestsAdapter {
	@Autowired
	private ContentSecurityClient contentSecurityClient;
	
	@Test
	public void testImgSecCheck() throws Exception{
		AccessTokenInfo accessToken = getAccessToken();
		System.out.println("tokenInfo:"+accessToken);
		Resource media = new FileSystemResource("d:/test/test.jpg");
		WechatResponse res = contentSecurityClient.imgSecCheck(accessToken, media);
		System.out.println("res:" + res);
		assertThat(res.isSuccess()).isTrue();
		
		media = new FileSystemResource("d:/test/很黄很黄.png");
		res = contentSecurityClient.imgSecCheck(accessToken, media);
		System.out.println("res:" + res);
		assertThat(res.isSuccess()).isTrue();
	}
	
	@Test
	public void testImgSecCheckWithBytes() throws Exception{
		AccessTokenInfo accessToken = getAccessToken();
		System.out.println("tokenInfo:"+accessToken);
		Resource resource = new FileSystemResource("d:/test/test.jpg");
		ByteArrayResource media = new FileNameByteArrayResource("test.jpg", FileUtils.toByteArray(resource.getInputStream()));
		WechatResponse res = contentSecurityClient.imgSecCheck(accessToken, media);
		System.out.println("res:" + res);
		assertThat(res.isSuccess()).isTrue();
	}
	
	@Test
	public void testMsgSecCheck() throws Exception{
		String content = "我是党员老江";
		AccessTokenInfo accessToken = getAccessToken();
		WechatResponse res = contentSecurityClient.msgSecCheck(accessToken, content);
		assertThat(res.isSuccess()).isTrue();

		assertThatExceptionOfType(ApiClientException.class).isThrownBy(()->{
			contentSecurityClient.msgSecCheck(accessToken, "我是党员江泽民");
		});
		res = contentSecurityClient.msgSecCheck(accessToken, "我是党员江他喵的泽民");
		assertThat(res.isSuccess()).isTrue();
	}
	

}
