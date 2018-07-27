package org.onetwo.ext.apiclient.wechat.article.api;

import org.onetwo.common.utils.FieldName;
import org.onetwo.ext.apiclient.wechat.article.vo.req.AddNewsBody;
import org.onetwo.ext.apiclient.wechat.article.vo.req.MaterialListBody;
import org.onetwo.ext.apiclient.wechat.article.vo.req.SendAllBody;
import org.onetwo.ext.apiclient.wechat.article.vo.resp.*;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文档地址：主要是【素材管理】和【消息管理】<br>
 *     https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1445241432
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
    @PostMapping(value = "/material/add_material?access_token={accessToken}&type={type}", consumes = {"multipart/form-data"})
    AddMaterialResBody addMaterial(@PathVariable("accessToken") String accessToken, @PathVariable("type") String type, @FieldName("media") Resource media);

    /**
     * 新增永久图文素材<br>
     * 不知道和上传图文消息素材有什么不同
     */
    @PostMapping(path = "/material/add_news?access_token={accessToken}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    AddNewsResBody addNews(@PathVariable("accessToken") String accessToken, @Validated @RequestBody AddNewsBody body);

    /**
     * 上传图文消息内的图片获取URL
     */
    @PostMapping(value = "/media/uploadimg?access_token={accessToken}",consumes = {"multipart/form-data"})
    UploadImgResBody uploadImg(@PathVariable("accessToken") String accessToken,@FieldName("media") Resource media);

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
