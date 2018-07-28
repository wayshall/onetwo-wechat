package org.onetwo.ext.apiclient.wechat.material.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.material.request.AddVideoMaterialRequest;
import org.onetwo.ext.apiclient.wechat.material.request.BatchgetMaterialRequest;
import org.onetwo.ext.apiclient.wechat.material.response.AddMaterialResponse;
import org.onetwo.ext.apiclient.wechat.material.response.AddNewsResponse;
import org.onetwo.ext.apiclient.wechat.material.response.BatchgetMaterialResponse;
import org.onetwo.ext.apiclient.wechat.media.request.AddNewsRequest;
import org.onetwo.ext.apiclient.wechat.media.request.AddNewsRequest.AddNewsItem;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

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
    public void testAddNews() {
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

        AddNewsResponse resBody = articleTestClient.addNews(accessTokenInfo, body);
        toJsonString("新增永久图文素材", resBody);
        assertThat(resBody.isSuccess()).isTrue();
        assertThat(resBody.getMediaId()).isNotNull();
    }
    
   
}
