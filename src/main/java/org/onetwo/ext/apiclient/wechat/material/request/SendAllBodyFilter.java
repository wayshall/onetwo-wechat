package org.onetwo.ext.apiclient.wechat.material.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author LeeKITMAN
 */
@Data
public class SendAllBodyFilter {

    /**
     * 用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，选择false可根据tag_id发送给指定群组的用户
     */
    @NotNull
    private Boolean is_to_all;

    /**
     * 群发到的标签的tag_id，参见用户管理中用户分组接口，若is_to_all值为true，可不填写tag_id
     */
    private String tag_id;

    public SendAllBodyFilter(Boolean is_to_all, String tag_id) {
        this.is_to_all = is_to_all;
        this.tag_id = tag_id;
    }

    public SendAllBodyFilter() {
    }
}
