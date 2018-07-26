package org.onetwo.ext.apiclient.wechat.article.vo.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

/**
 * @author LeeKITMAN
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UploadImgResBody extends WechatResponse {

    protected String url;
}
