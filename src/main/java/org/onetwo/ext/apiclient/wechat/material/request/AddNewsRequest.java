package org.onetwo.ext.apiclient.wechat.material.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author LeeKITMAN
 */
@Data
public class AddNewsRequest {

    @NotNull
    private List<AddNewsItem> articles;
    
    @Data
    static public class AddNewsItem {

        @NotNull
        private String title;
        @NotNull
        @JsonProperty("thumb_media_id")
        private String thumbMediaId;
        private String author;
        private String digest;
        @NotNull
        @JsonProperty("show_cover_pic")
        private Boolean showCoverPic;
        @NotNull
        private String content;
        @NotNull
        @JsonProperty("content_source_url")
        private String contentSourceUrl;

    }

}
