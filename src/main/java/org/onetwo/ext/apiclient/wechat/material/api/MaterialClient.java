package org.onetwo.ext.apiclient.wechat.material.api;

import org.onetwo.common.annotation.FieldName;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.material.request.AddVideoMaterialRequest;
import org.onetwo.ext.apiclient.wechat.material.request.BatchgetMaterialRequest;
import org.onetwo.ext.apiclient.wechat.material.response.AddMaterialResponse;
import org.onetwo.ext.apiclient.wechat.material.response.AddNewsResponse;
import org.onetwo.ext.apiclient.wechat.material.response.BatchgetMaterialResponse;
import org.onetwo.ext.apiclient.wechat.media.request.AddNewsRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 文档地址：主要是【素材管理】和【消息管理】<br>
 *     https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738726
 */
@WechatApiClient
public interface MaterialClient {

    /**
     * 获取素材列表
     */
    @PostMapping(path = "/material/batchget_material", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    BatchgetMaterialResponse batchgetMaterial(AccessTokenInfo accessToken, @Validated @RequestBody BatchgetMaterialRequest body);

    /**
     * 新增其他类型永久素材：<br>
     * 图片（image）、语音（voice）、视频（video）和缩略图（thumb）
     * @param media form-data中媒体文件标识
     */
    @PostMapping(value = "/material/add_material", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    AddMaterialResponse addMaterial(AccessTokenInfo accessToken, 
    								@RequestParam("type") String type, 
    								@FieldName("media") Resource media);
   /***
    * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444738729
    * 
    * 新增永久视频素材
    * @author wayshall
    * @param accessToken
    * @param type
    * @param media
    * @param description
    * @return
    */
   @PostMapping(value = "/material/add_material", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
   AddMaterialResponse addMaterial(AccessTokenInfo accessToken, 
   								@RequestParam("type") String type, 
   								@FieldName("media") Resource media,
   								AddVideoMaterialRequest description);

    /**
     * 新增永久图文素材<br>
     * 不知道和上传图文消息素材有什么不同
     */
    @PostMapping(path = "/material/add_news",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    AddNewsResponse addNews(AccessTokenInfo accessToken, @Validated @RequestBody AddNewsRequest body);

}
