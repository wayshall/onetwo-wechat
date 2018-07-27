package org.onetwo.ext.apiclient.wechat.material.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

/**
 * @author LeeKITMAN
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SendAllResBody extends WechatResponse {

    private Integer errcode;
    private String errmsg;
    private String msg_id;
    private String msg_data_id;
}
