package org.onetwo.ext.apiclient.wechat.article.api;

import org.onetwo.ext.apiclient.wechat.article.vo.req.AddNewsBody;
import org.onetwo.ext.apiclient.wechat.article.vo.req.MaterialListBody;
import org.onetwo.ext.apiclient.wechat.article.vo.req.SendAllBody;
import org.onetwo.ext.apiclient.wechat.article.vo.resp.*;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LeeKITMAN
 */
@WechatApiClient
public interface ArticleTestClient {

    /**
     * 获取素材列表
     */
    @PostMapping(path = "/material/batchget_material?access_token={accessToken}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    MaterialListResBody getMaterialList(@PathVariable("accessToken") String accessToken, @Validated @RequestBody MaterialListBody body);

    /**
     * 新增其他类型永久素材：<br>
     * 图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     */
    @PostMapping(path = "/material/add_material?access_token={accessToken}&type={type}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    AddMaterialResBody addMaterial(@PathVariable("accessToken") String accessToken, @PathVariable("type") String type, @RequestParam("media") MultipartFile media);

    /**
     * 新增永久图文素材<br>
     * 不知道和上传图文消息素材有什么不同
     */
    @PostMapping(path = "/material/add_news?access_token={accessToken}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    AddNewsResBody addNews(@PathVariable("accessToken") String accessToken, @Validated @RequestBody AddNewsBody body);

    /**
     * 上传图文消息内的图片获取URL
     */
    @PostMapping(path = "/media/uploadimg?access_token={accessToken}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UploadImgResBody uploadImg(@PathVariable("accessToken") String accessToken, @RequestParam("media") MultipartFile media);

    /**
     * 上传图文消息素材<br>
     * 不知道和新增永久图文素材有什么不同
     */
    @PostMapping(path = "/media/uploadnews?access_token={accessToken}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    UploadNewsResBody uploadNews(@PathVariable("accessToken") String accessToken, @Validated @RequestBody AddNewsBody body);

    /**
     * 根据标签进行群发
     */
    @PostMapping(path = "/message/mass/sendall?access_token={accessToken}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    SendAllResBody sendAll(@PathVariable("accessToken") String accessToken, @Validated @RequestBody SendAllBody body);

}
