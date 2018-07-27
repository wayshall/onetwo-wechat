package org.onetwo.ext.apiclient.wechat.article.vo.resp;

import lombok.Data;

import java.util.List;

/**
 * @author LeeKITMAN
 */
@Data
public class MaterialListResBodyItemContent {
    private List<MaterialListResBodyItemContentNewsItem> news_item;
}
