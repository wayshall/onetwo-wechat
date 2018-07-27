package org.onetwo.ext.apiclient.wechat.material.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author LeeKITMAN
 * @author wayshall
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BatchgetMaterialResponse extends WechatResponse {

	@JsonProperty("total_count")
    private Integer totalCount;
	@JsonProperty("item_count")
    private Integer itemCount;
    private List<MaterialItem> item;
}
