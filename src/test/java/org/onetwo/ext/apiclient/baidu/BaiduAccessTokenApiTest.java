package org.onetwo.ext.apiclient.baidu;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.onetwo.ext.apiclient.baidu.api.OcrClient;
import org.onetwo.ext.apiclient.baidu.core.BaiduAccessTokenProvider.BaiduAccessTokenTypes;
import org.onetwo.ext.apiclient.baidu.core.BaiduAppConfig;
import org.onetwo.ext.apiclient.baidu.request.LicensePlateRequest;
import org.onetwo.ext.apiclient.baidu.response.LicensePlateResponse;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class BaiduAccessTokenApiTest extends BaiduBaseBootTests {
	
	@Autowired
	AccessTokenService accessTokenService;
	@Autowired
	BaiduAppConfig appConfig;
	
	AccessTokenInfo accessToken;
	@Value("${baidu.apps.default.appid}")
	String appid;
	@Autowired
	OcrClient ocrClient;
	
	
	public AccessTokenInfo getAccessToken() {
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
											.appid(appid)
											.accessTokenType(BaiduAccessTokenTypes.BAIDU_BCE)
											.build();
		AccessTokenInfo token = accessTokenService.getOrRefreshAccessToken(request);
		assertThat(token).isNotNull();
		System.out.println("token: " + token);
		assertThat(token.getAccessToken()).isNotEmpty();
		return token;
	}

	@Before
	public void setup() {
		this.accessToken = getAccessToken();
	}
	
	@Test
	public void testLicensePlate() {
		LicensePlateRequest request = new LicensePlateRequest();
		request.setUrl("https://static.ai-parking.cn/parkingiot/roadtooth/8b05d030-10d2-4c6c-b17f-1ed11f5346ce.jpg");
		LicensePlateResponse res = ocrClient.licensePlate(accessToken, request);
		System.out.println("res: " + res);
	}
	

}

