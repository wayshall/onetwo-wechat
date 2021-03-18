package org.onetwo.ext.apiclient.yly;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.onetwo.ext.apiclient.yly.api.PrintClient;
import org.onetwo.ext.apiclient.yly.api.PrinterClient;
import org.onetwo.ext.apiclient.yly.core.YlyAccessTokenProvider.YlyAccessTokenTypes;
import org.onetwo.ext.apiclient.yly.core.YlyAppConfig;
import org.onetwo.ext.apiclient.yly.request.AddPrinterRequest;
import org.onetwo.ext.apiclient.yly.request.PrintTextRequest;
import org.onetwo.ext.apiclient.yly.request.PrinterRequest;
import org.onetwo.ext.apiclient.yly.response.PrinterStateResponse;
import org.onetwo.ext.apiclient.yly.response.YlyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class YlyAccessTokenApiTest extends YlyBaseBootTests {
	
	@Autowired
	AccessTokenService accessTokenService;
	@Autowired
	PrintClient printClient;
	@Autowired
	YlyAppConfig appConfig;
	@Autowired
	PrinterClient printerClient;
	
	AccessTokenInfo accessToken;
	@Value("${yilianyun.apps.default.appid}")
	String appid;
	
	@Value("${yilianyun.testContent}")
	String content;
	
	public AccessTokenInfo getAccessToken() {
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
											.appid(appid)
											.accessTokenType(YlyAccessTokenTypes.YI_LIAN_YUN)
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
	public void testAddPrinter() {
		AddPrinterRequest request = AddPrinterRequest.builder()
													.clientId(appid)
													.accessToken(accessToken.getAccessToken())
													.machineCode(appConfig.getAppConfig(appid).getConfig("machine-code"))
													.msign(appConfig.getAppConfig(appid).getConfig("machine-secrect"))
													.build();
		request.sign(appConfig);
		YlyResponse res = printerClient.addPrinter(request);
		assertThat(res.isSuccess()).isTrue();
	}
	
	@Test
	public void testBtnprint() {
		PrinterRequest request = PrinterRequest.builder()
											.accessToken(accessToken.getAccessToken())
											.clientId(appid)
											.machineCode(appConfig.getAppConfig(appid).getConfig("machine-code"))
//											.responseType(ResponseTypes.btnclose.name())
											.build();
		request.sign(appConfig);
		PrinterStateResponse res = printerClient.getPrintStatus(request);
		System.out.println("state: " + res);
		assertThat(res.isSuccess()).isTrue();
	}
	
	@Test
	public void testPrintText() {
		PrintTextRequest request = PrintTextRequest.builder()
											.accessToken(accessToken.getAccessToken())
											.clientId(appid)
											.machineCode(appConfig.getAppConfig(appid).getConfig("machine-code"))
											.content(content)
											.originId("12")
											.build();
		request.sign(appConfig);
		this.printClient.printText(accessToken, request);
	}

}

