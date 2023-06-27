package org.onetwo.ext.apiclient.baidu;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.Before;
import org.junit.Test;
import org.onetwo.common.apiclient.utils.ApiClientMethodConfigs;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.baidu.api.OcrClient;
import org.onetwo.ext.apiclient.baidu.core.BaiduAccessTokenProvider.BaiduAccessTokenTypes;
import org.onetwo.ext.apiclient.baidu.core.BaiduAppConfig;
import org.onetwo.ext.apiclient.baidu.request.LicensePlateRequest;
import org.onetwo.ext.apiclient.baidu.response.LicensePlateResponse;
import org.onetwo.ext.apiclient.baidu.util.BaiduUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class BaiduOCRApiTest extends BaiduBaseBootTests {
	
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
		assertThat(res.isSuccess()).isTrue();
		
		request = new LicensePlateRequest();
		request.setApiConfig(ApiClientMethodConfigs.DISABLED_THROW_IF_ERROR);
		request.setUrl("https://static.ai-parking.cn/parkingiot/roadtooth/2023-06-27/1a9492e2-b1f5-4f4b-942e-68bf9d542e2a.jpg");
		res = ocrClient.licensePlate(accessToken, request);
		assertThat(res.isSuccess()).isFalse();
		
		
		assertThatExceptionOfType(ApiClientException.class).isThrownBy(() -> {
			LicensePlateRequest request2 = new LicensePlateRequest();
			request2.setUrl("https://static.ai-parking.cn/parkingiot/roadtooth/2023-06-27/1a9492e2-b1f5-4f4b-942e-68bf9d542e2a.jpg");
			ocrClient.licensePlate(accessToken, request2);
		});
	}

	@Test
	public void testLicensePlateWithImage() {
		LicensePlateRequest request = new LicensePlateRequest();
		request.setImage(BaiduUtils.encodeImage("/Users/way/mydev/测试数据/车位设备/image1.png"));
		LicensePlateResponse res = ocrClient.licensePlate(accessToken, request);
		System.out.println("res: " + res);
		
		request = new LicensePlateRequest();
		request.setImage(BaiduUtils.encodeImage("/Users/way/mydev/测试数据/车位设备/image2.png"));
		res = ocrClient.licensePlate(accessToken, request);
		System.out.println("res: " + res);
	}

	@Test
	public void testLicenseNewPlate() {
		LicensePlateRequest request = new LicensePlateRequest();
		request.setImage(BaiduUtils.encodeImage("/Users/way/mydev/测试数据/车位设备/new_1.jpg"));
		LicensePlateResponse res = ocrClient.licensePlate(accessToken, request);
		System.out.println("res: " + res);
		
//		request = new LicensePlateRequest();
//		request.setImage(BaiduUtils.encodeImage("/Users/way/mydev/测试数据/车位设备/image2.png"));
//		res = ocrClient.licensePlate(accessToken, request);
//		System.out.println("res: " + res);
	}
	

}

