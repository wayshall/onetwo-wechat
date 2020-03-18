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
import org.onetwo.ext.apiclient.yly.client.PrintClient;
import org.onetwo.ext.apiclient.yly.client.PrintClient.PrintTextRequest;
import org.onetwo.ext.apiclient.yly.client.PrinterClient;
import org.onetwo.ext.apiclient.yly.client.PrinterClient.AddPrinterRequest;
import org.onetwo.ext.apiclient.yly.core.YlyAccessTokenProvider.YlyAccessTokenTypes;
import org.onetwo.ext.apiclient.yly.core.YlyAppConfig;
import org.onetwo.ext.apiclient.yly.core.YlyResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class YlyAccessTokenApiTest extends YlyBaseBootTests {
	
	@Autowired
	AccessTokenService accessTokenService;
	@Autowired
	PrintClient printClient;
	@Autowired
	YlyAppConfig appConfig;
	@Autowired
	PrinterClient printerClient;
	
	String accessToken = "4043ed049f5645199ac8e5c27918f500";
	
	public AccessTokenInfo getAccessToken() {
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
											.appid("1049868864")
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
		this.accessToken = getAccessToken().getAccessToken();
	}
	
	@Test
	public void testAddPrinter() {
		AddPrinterRequest request = AddPrinterRequest.builder()
													.clientId("1049868864")
													.accessToken(accessToken)
													.machineCode(appConfig.getAppConfig("1049868864").getConfig("machine-code"))
													.msign(appConfig.getAppConfig("1049868864").getConfig("machine-secrect"))
													.build();
		request.sign(appConfig);
		YlyResponse res = printerClient.addPrinter(request);
		assertThat(res.isSuccess()).isTrue();
	}
	
	@Test
	public void testPrintText() {
		PrintTextRequest request = PrintTextRequest.builder()
											.accessToken(accessToken)
											.clientId("1049868864")
											.machineCode("4004646641")
											.content("<center>test</center>")
											.originId("test")
											.build();
		request.sign(appConfig);
		this.printClient.printText(request);
	}

}

