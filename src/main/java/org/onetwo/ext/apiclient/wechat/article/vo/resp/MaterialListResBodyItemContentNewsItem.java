package org.onetwo.ext.apiclient.wechat.article.vo.resp;

import lombok.Data;

/**
 * @author LeeKITMAN
 */
@Data
public class MaterialListResBodyItemContentNewsItem {

    private String title;
    private String thumb_media_id;
    private Boolean show_cover_pic;
    private String author;
    private String digest;
    private String content;
    private String url;
    private String content_source_url;
}
