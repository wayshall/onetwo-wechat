package org.onetwo.ext.apiclient.wechat.article.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.onetwo.common.apiclient.resouce.FileNameByteArrayResource;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.article.vo.req.*;
import org.onetwo.ext.apiclient.wechat.article.vo.resp.*;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LeeKITMAN
 */
@Slf4j
public class ArticleTestClientTest extends WechatBaseTestsAdapter {

    @Autowired
    private ArticleTestClient articleTestClient;
    @Autowired
    AccessTokenService accessTokenService;

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
    public void getMaterialListTest() {
        String token = accessTokenService.getAccessToken().getAccessToken();
        
        MaterialListBody body = new MaterialListBody();
        body.setType("image");
        body.setOffset(0);
        body.setCount(20);

        MaterialListResBody resBody = articleTestClient.getMaterialList(token, body);
        toJsonString("获取素材列表", resBody);
    }

    /**
     * 新增其他类型永久素材
     */
    @Test
    public void addMaterialTest() throws Exception {
        String token = accessTokenService.getAccessToken().getAccessToken();

        // File转换成MutipartFile
        File file = new File("E:\\temp\\桌面\\图片\\美食图片\\29f137c0c24b401c1c55a3966ed0a23e.jpg");
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile media = new MockMultipartFile(file.getName(), inputStream);
        ByteArrayResource byteArrayResource = new FileNameByteArrayResource(media.getOriginalFilename(), media.getBytes());

        AddMaterialResBody resBody = articleTestClient.addMaterial(token, "image", byteArrayResource);
        toJsonString("新增其他类型永久素材", resBody);
    }

    /**
     * 新增永久图文素材
     */
    @Test
    public void addNewsTest() {
        String token = accessTokenService.getAccessToken().getAccessToken();
        
        AddNewsBody body = new AddNewsBody();
        List<AddNewsBodyItem> list = new ArrayList<>();
        AddNewsBodyItem articles = new AddNewsBodyItem();
        articles.setTitle("测试标题");
        articles.setThumb_media_id("kQiTKiCLHlfN6aKTMJ-IP19S1efmmNsm2l3C8NuuTB4");
        articles.setAuthor("leekitman");
        articles.setShow_cover_pic(true);
        articles.setContent("噫吁嚱，危乎高哉！蜀道之难，难于上青天！蚕丛及鱼凫，开国何茫然！尔来四万八千岁，不与秦塞通人烟。西当太白有鸟道，可以横绝峨眉巅。");
        articles.setContent_source_url("http://d4q5ux.natappfree.cc");
        list.add(articles);
        body.setArticles(list);

        AddNewsResBody resBody = articleTestClient.addNews(token, body);
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

        UploadImgResBody resBody = articleTestClient.uploadImg(token, byteArrayResource);
        toJsonString("上传图文消息内的图片获取URL", resBody);
    }

    /**
     * 上传图文消息素材
     */
    @Test
    public void uploadNewsTest(){
        String token = accessTokenService.getAccessToken().getAccessToken();

        AddNewsBody body = new AddNewsBody();
        List<AddNewsBodyItem> list = new ArrayList<>();
        AddNewsBodyItem articles = new AddNewsBodyItem();
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
