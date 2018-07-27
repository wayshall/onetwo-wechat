package org.onetwo.ext.apiclient.wechat.article.vo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author LeeKITMAN
 */
@Data
public class AddNewsBodyItem {

    @NotNull
    private String title;
    @NotNull
    private String thumb_media_id;
    private String author;
    private String digest;
    @NotNull
    private Boolean show_cover_pic;
    @NotNull
    private String content;
    @NotNull
    private String content_source_url;

}
