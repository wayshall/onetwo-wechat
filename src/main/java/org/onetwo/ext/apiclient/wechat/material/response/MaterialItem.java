package org.onetwo.ext.apiclient.wechat.material.response;

import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * 永久图文消息素材列表的响应如下：

{
   "total_count": TOTAL_COUNT,
   "item_count": ITEM_COUNT,
   "item": [{
       "media_id": MEDIA_ID,
       "content": {
           "news_item": [{
               "title": TITLE,
               "thumb_media_id": THUMB_MEDIA_ID,
               "show_cover_pic": SHOW_COVER_PIC(0 / 1),
               "author": AUTHOR,
               "digest": DIGEST,
               "content": CONTENT,
               "url": URL,
               "content_source_url": CONTETN_SOURCE_URL
           },
           //多图文消息会在此处有多篇文章
           ]
        },
        "update_time": UPDATE_TIME
    },
    //可能有多个图文消息item结构
  ]
}

 * 其他类型（图片、语音、视频）的返回如下：
 * {
   "total_count": TOTAL_COUNT,
   "item_count": ITEM_COUNT,
   "item": [{
       "media_id": MEDIA_ID,
       "name": NAME,
       "update_time": UPDATE_TIME,
       "url":URL
   },
   //可能会有多个素材
   ]
}
 * @author LeeKITMAN
 * @author wayshall
 */
@Data
public class MaterialItem {
	@JsonProperty("media_id")
    private String mediaId;
	/***
	 * 永久图文消息素材
	 */
    private MaterialItemContent content;
    
    //其他类型
    private String name;
    private String url;
	@JsonProperty("update_time")
    private Date updateTime;
}
