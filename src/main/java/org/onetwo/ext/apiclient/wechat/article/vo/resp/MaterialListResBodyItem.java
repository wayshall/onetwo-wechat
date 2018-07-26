package org.onetwo.ext.apiclient.wechat.article.vo.resp;

import lombok.Data;

import java.util.Date;

/**
 * @author LeeKITMAN
 */
@Data
public class MaterialListResBodyItem {

    private String media_id;
    private MaterialListResBodyItemContent content;
    private Date update_time;
}
