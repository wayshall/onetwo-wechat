package org.onetwo.ext.apiclient.wechat.material.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author LeeKITMAN
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AddMaterialResponse extends UploadImgResponse {
	@JsonProperty("media_id")
    private String mediaId;
}
