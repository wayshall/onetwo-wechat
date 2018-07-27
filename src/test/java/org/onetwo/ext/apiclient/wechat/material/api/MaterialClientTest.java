package org.onetwo.ext.apiclient.wechat.material.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;
import org.onetwo.common.apiclient.resouce.FileNameByteArrayResource;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.material.request.AddNewsRequest;
import org.onetwo.ext.apiclient.wechat.material.request.AddNewsRequest.AddNewsItem;
import org.onetwo.ext.apiclient.wechat.material.request.AddVideoMaterialRequest;
import org.onetwo.ext.apiclient.wechat.material.request.BatchgetMaterialRequest;
import org.onetwo.ext.apiclient.wechat.material.request.SendAllBody;
import org.onetwo.ext.apiclient.wechat.material.request.SendAllBodyFilter;
import org.onetwo.ext.apiclient.wechat.material.request.SendAllBodyMpNews;
import org.onetwo.ext.apiclient.wechat.material.response.AddMaterialResponse;
import org.onetwo.ext.apiclient.wechat.material.response.AddNewsResponse;
import org.onetwo.ext.apiclient.wechat.material.response.BatchgetMaterialResponse;
import org.onetwo.ext.apiclient.wechat.material.response.SendAllResBody;
import org.onetwo.ext.apiclient.wechat.material.response.UploadImgResponse;
import org.onetwo.ext.apiclient.wechat.material.response.UploadNewsResBody;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author LeeKITMAN
 */
@Slf4j
public class MaterialClientTest extends WechatBaseTestsAdapter {

    @Autowired
    private MaterialClient articleTestClient;
    @Autowired
    AccessTokenService accessTokenService;

    AccessTokenInfo accessTokenInfo;
    
    @Before
    public void setup(){
    	this.accessTokenInfo = accessTokenService.getAccessToken();
    }

    private void toJsonString(String msg, Object resBody) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info(msg + "，返回结果={}", mapper.writeValueAsString(resBody));
        } catch (JsonProcessingException e) {
            log.error("json转化错误。", e);
        }
    }

    /**
     * 获取素材列表
     */
    @Test
    public void testGetMaterialList() {
        BatchgetMaterialRequest body = new BatchgetMaterialRequest();
        body.setType("image");
        body.setOffset(0);
        body.setCount(20);

        BatchgetMaterialResponse resBody = articleTestClient.batchgetMaterial(accessTokenInfo, body);
        toJsonString("获取素材列表", resBody);
        assertThat(resBody.isSuccess()).isTrue();
        assertThat(resBody.getItem()).isNotEmpty();
        assertThat(resBody.getItem().get(0).getMediaId()).isNotNull();
    }

    /**
     * 新增其他类型永久素材
     */
    @Test
    public void testAddMaterial() throws Exception {
    	Resource res = new ClassPathResource("img/kq.jpg");
//        ByteArrayResource byteArrayResource = new FileNameByteArrayResource("kq.jpg", FileUtils.toByteArray(res.getInputStream()));
        AddMaterialResponse resBody = articleTestClient.addMaterial(accessTokenInfo, "image", res);
        toJsonString("新增其他类型永久素材", resBody);
        assertThat(resBody.isSuccess()).isTrue();
        assertThat(resBody.getMediaId()).isNotNull();
        assertThat(resBody.getUrl()).isNotNull();
        
    }

    /**
     * 新增其他类型永久素材
     */
    @Test
    public void testAddMaterial2() throws Exception {
        AddVideoMaterialRequest video = new AddVideoMaterialRequest();
        video.setTitle("test");
        video.setIntroduction("test");
        
    	Resource res = new ClassPathResource("img/kq.jpg");
//        ByteArrayResource byteArrayResource = new FileNameByteArrayResource("kq.jpg", FileUtils.toByteArray(res.getInputStream()));
        AddMaterialResponse resBody = articleTestClient.addMaterial(accessTokenInfo, "image", res, video);
        toJsonString("新增其他类型永久素材", resBody);
        assertThat(resBody.isSuccess()).isTrue();
        assertThat(resBody.getMediaId()).isNotNull();
        assertThat(resBody.getUrl()).isNotNull();
    }

    /**
     * 新增永久图文素材
     */
    @Test
    public void addNewsTest() {
        String token = accessTokenService.getAccessToken().getAccessToken();
        
        AddNewsRequest body = new AddNewsRequest();
        List<AddNewsItem> list = new ArrayList<>();
        AddNewsItem articles = new AddNewsItem();
        articles.setTitle("测试标题");
        articles.setThumbMediaId("kQiTKiCLHlfN6aKTMJ-IP19S1efmmNsm2l3C8NuuTB4");
        articles.setAuthor("leekitman");
        articles.setShowCoverPic(true);
        articles.setContent("噫吁嚱，危乎高哉！蜀道之难，难于上青天！蚕丛及鱼凫，开国何茫然！尔来四万八千岁，不与秦塞通人烟。西当太白有鸟道，可以横绝峨眉巅。");
        articles.setContentSourceUrl("http://d4q5ux.natappfree.cc");
        list.add(articles);
        body.setArticles(list);

        AddNewsResponse resBody = articleTestClient.addNews(token, body);
        toJsonString("新增永久图文素材", resBody);
    }

    /**
     * 上传图文消息内的图片获取URL
     */
    @Test
    public void uploadImgTest() throws Exception {
        String token = accessTokenService.getAccessToken().getAccessToken();

        // File转换成MutipartFile
        File file = new File("E:\\temp\\桌面\\图片\\美食图片\\29f137c0c24b401c1c55a3966ed0a23e.jpg");
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile media = new MockMultipartFile(file.getName(), inputStream);
        ByteArrayResource byteArrayResource = new FileNameByteArrayResource(media.getOriginalFilename(), media.getBytes());

        UploadImgResponse resBody = articleTestClient.uploadImg(token, byteArrayResource);
        toJsonString("上传图文消息内的图片获取URL", resBody);
    }

    /**
     * 上传图文消息素材
     */
    @Test
    public void uploadNewsTest(){
        String token = accessTokenService.getAccessToken().getAccessToken();

        AddNewsRequest body = new AddNewsRequest();
        List<AddNewsItem> list = new ArrayList<>();
        AddNewsItem articles = new AddNewsItem();
        articles.setTitle("测试标题upload");
        articles.setThumb_media_id("kQiTKiCLHlfN6aKTMJ-IP19S1efmmNsm2l3C8NuuTB4");
        articles.setAuthor("leekitman");
        articles.setShow_cover_pic(true);
        articles.setContent("upload噫吁嚱，危乎高哉！蜀道之难，难于上青天！蚕丛及鱼凫，开国何茫然！尔来四万八千岁，不与秦塞通人烟。西当太白有鸟道，可以横绝峨眉巅。");
        articles.setContent_source_url("http://d4q5ux.natappfree.cc");
        list.add(articles);
        body.setArticles(list);

        UploadNewsResBody resBody = articleTestClient.uploadNews(token, body);
        toJsonString("上传图文消息素材", resBody);
    }

    /**
     * 根据标签进行群发
     */
    @Test
    public void sendAllTest(){
        String token = accessTokenService.getAccessToken().getAccessToken();

        SendAllBody body = new SendAllBody();
        body.setFilter(new SendAllBodyFilter(true, null));
        SendAllBodyMpNews mpNews = new SendAllBodyMpNews();
        mpNews.setMedia_id("kQiTKiCLHlfN6aKTMJ-IP7yYyxVx1qA61BfJ1fdAd2g");
        body.setMpnews(mpNews);
        body.setMsgtype("mpnews");
        body.setSend_ignore_reprint(0);

        SendAllResBody resBody = articleTestClient.sendAll(token, body);
        toJsonString("根据标签进行群发", resBody);
    }
}
