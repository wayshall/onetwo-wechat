package org.onetwo.ext.apiclient.wechat.material.response;

import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author LeeKITMAN
 * @author wayshall
 */
@Data
public class MaterialItemContent {
	@JsonProperty("news_item")
    private List<NewsItem> newsItem;
	
	@Data
	public static class NewsItem {

	    private String title;
		@JsonProperty("thumb_media_id")
	    private String thumbMediaId;
		@JsonProperty("show_cover_pic")
	    private Boolean showCoverPic;
	    private String author;
	    private String digest;
	    private String content;
	    private String url;
		@JsonProperty("content_source_url")
	    private String contentSourceUrl;
	}
}
