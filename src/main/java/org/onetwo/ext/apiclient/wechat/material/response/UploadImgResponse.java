package org.onetwo.ext.apiclient.wechat.material.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

/**
 * @author LeeKITMAN
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UploadImgResponse extends WechatResponse {

    protected String url;
}
