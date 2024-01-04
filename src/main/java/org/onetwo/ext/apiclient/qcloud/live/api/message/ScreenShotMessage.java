package org.onetwo.ext.apiclient.qcloud.live.api.message;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * （200）新截图文件
 * 
 * @author wayshall
 * 
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class ScreenShotMessage extends MessageHeader {
	/***
	 * 图片地址，不带域名的路径
	 */
	@NotNull
	@JsonProperty("pic_url")
	String picUrl;
	
	/***
	 * 截图时间戳,截图时间戳（unix 时间戳，由于 I 帧干扰，暂时不能精确到秒级）
	 */
	@JsonProperty("create_time")
	int createTime;
	
	/***
	 * 截图全路径
	 * 需要带上域名
	 */
	@JsonProperty("pic_full_url")
	String picFullUrl;

}
