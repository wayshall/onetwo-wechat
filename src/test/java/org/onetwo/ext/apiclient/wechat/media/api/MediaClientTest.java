package org.onetwo.ext.apiclient.wechat.media.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.onetwo.common.file.FileUtils;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.material.response.UploadNewsResponse;
import org.onetwo.ext.apiclient.wechat.media.request.AddNewsRequest;
import org.onetwo.ext.apiclient.wechat.media.request.AddNewsRequest.AddNewsItem;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MediaTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * @author wayshall
 * <br/>
 */
public class MediaClientTest extends WechatBaseTestsAdapter {
	@Autowired
	private MediaClient mediaClient;
	@Autowired
	private AccessTokenService accessTokenService;
	

	@Test
	public void testUploadUrl() throws Exception{
		Resource buffer = new UrlResource("https://test-1252097922.cos.ap-guangzhou.myqcloud.com/club_album-711891c1-2a8e-4637-94d5-c7d2de86617d.png");
		UploadNewsResponse res = this.mediaClient.upload(accessTokenInfo, MediaTypes.IMAGE, buffer);
		System.out.println("res:"+res);
		assertThat(res.isSuccess()).isTrue();
		assertThat(res.getMediaId()).isNotNull();

		String mediaId = res.getMediaId();
		ByteArrayResource byteRes = mediaClient.get(accessTokenInfo, mediaId);
		FileUtils.writeByteArrayToFile(new File("D:/test/media-get.jpg"), byteRes.getByteArray());
	}
	
	@Test
	public void testUploadNews(){
		Resource buffer = new ClassPathResource("img/kq.jpg");
		UploadNewsResponse res = this.mediaClient.upload(accessTokenInfo, MediaTypes.IMAGE, buffer);
		System.out.println("res:"+res);
		assertThat(res.isSuccess()).isTrue();
		assertThat(res.getMediaId()).isNotNull();
		
		AddNewsRequest body = new AddNewsRequest();
        List<AddNewsItem> list = new ArrayList<>();
        AddNewsItem articles = new AddNewsItem();
        articles.setTitle("测试标题upload");
//        articles.setThumbMediaId("kQiTKiCLHlfN6aKTMJ-IP19S1efmmNsm2l3C8NuuTB4");
        articles.setThumbMediaId(res.getMediaId());
        articles.setAuthor("leekitman");
        articles.setShowCoverPic(true);
        articles.setContent("upload噫吁嚱，危乎高哉！蜀道之难，难于上青天！蚕丛及鱼凫，开国何茫然！尔来四万八千岁，不与秦塞通人烟。西当太白有鸟道，可以横绝峨眉巅。");
        articles.setContentSourceUrl("http://d4q5ux.natappfree.cc");
        list.add(articles);
        body.setArticles(list);

        UploadNewsResponse resBody = mediaClient.uploadNews(accessTokenInfo, body);
		System.out.println("resBody: " + resBody);
		
		/*assertThatExceptionOfType(ApiClientException.class)
		.isThrownBy(()->{
			
			AddNewsRequest body = new AddNewsRequest();
	        List<AddNewsItem> list = new ArrayList<>();
	        AddNewsItem articles = new AddNewsItem();
	        articles.setTitle("测试标题upload");
//	        articles.setThumbMediaId("kQiTKiCLHlfN6aKTMJ-IP19S1efmmNsm2l3C8NuuTB4");
	        articles.setThumbMediaId(res.getMediaId());
	        articles.setAuthor("leekitman");
	        articles.setShowCoverPic(true);
	        articles.setContent("upload噫吁嚱，危乎高哉！蜀道之难，难于上青天！蚕丛及鱼凫，开国何茫然！尔来四万八千岁，不与秦塞通人烟。西当太白有鸟道，可以横绝峨眉巅。");
	        articles.setContentSourceUrl("http://d4q5ux.natappfree.cc");
	        list.add(articles);
	        body.setArticles(list);

	        UploadNewsResponse resBody = mediaClient.uploadNews(accessTokenInfo, body);
			System.out.println("resBody: " + resBody);
		})
		.withMessage("api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限");*/
	}

}
