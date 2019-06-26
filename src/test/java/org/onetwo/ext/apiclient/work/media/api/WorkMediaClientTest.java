package org.onetwo.ext.apiclient.work.media.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;
import org.onetwo.common.file.FileUtils;
import org.onetwo.ext.apiclient.wechat.material.response.UploadNewsResponse;
import org.onetwo.ext.apiclient.wechat.media.response.UploadResponse;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MediaTypes;
import org.onetwo.ext.apiclient.work.WorkWechatBaseBootTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author weishao zeng
 * <br/>
 */
@ActiveProfiles("product")
public class WorkMediaClientTest extends WorkWechatBaseBootTests {
	
	@Autowired
	WorkMediaClient workMediaClient;
	

	
	@Test
	public void getGet() {
		String mediaId = "103iee-Uz5UhMz1MHtMWg-Fqqs1R_c3-aCXlRwkSju9BYfcuKrufIULttkfbbqlFQ";
		ByteArrayResource upRes = null;
		upRes = this.workMediaClient.get(getAccessToken(), mediaId);
		System.out.println("res:"+upRes.getFilename());//3zd4iU6whOG76O5BMjPoQi5-6P8Pd4Utq8m8SZ7Op1DA
		upRes = this.workMediaClient.getJssdk(getAccessToken(), mediaId);
		System.out.println("res:"+upRes.getFilename());//3zd4iU6whOG76O5BMjPoQi5-6P8Pd4Utq8m8SZ7Op1DA
		
	}
	
	@Test
	public void testUploadAndGet() {
		Resource buffer = new ClassPathResource("img/kq.jpg");
		UploadNewsResponse upRes = workMediaClient.upload(getAccessToken(), MediaTypes.IMAGE, buffer);
		System.out.println("res:"+upRes);//3zd4iU6whOG76O5BMjPoQi5-6P8Pd4Utq8m8SZ7Op1DA
		assertThat(upRes.isSuccess()).isTrue();
		assertThat(upRes.getMediaId()).isNotNull();
		
		File file = new File("D:\\test\\img\\test.jpg");
		ByteArrayResource res = this.workMediaClient.get(getAccessToken(), upRes.getMediaId());
		FileUtils.writeByteArrayToFile(file, res.getByteArray());
		System.out.println("res: " + res);
	}
	
	@Test
	public void testUploadimgAndGet() {
		Resource buffer = new ClassPathResource("img/kq.jpg");
		UploadResponse uploadRes = this.workMediaClient.uploadimg(accessTokenInfo, buffer);
		System.out.println("url: " + uploadRes.getUrl());
		assertThat(uploadRes.isSuccess()).isTrue();
		assertThat(uploadRes.getUrl()).isNotEmpty();
	}

}

