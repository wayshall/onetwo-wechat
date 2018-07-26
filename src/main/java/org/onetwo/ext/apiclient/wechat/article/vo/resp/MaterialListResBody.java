package org.onetwo.ext.apiclient.wechat.article.vo.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import java.util.List;

/**
 * @author LeeKITMAN
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MaterialListResBody extends WechatResponse {

    private Integer total_count;
    private Integer item_count;
    private List<MaterialListResBodyItem> item;
}
