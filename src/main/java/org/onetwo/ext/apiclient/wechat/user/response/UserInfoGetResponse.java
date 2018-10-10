package org.onetwo.ext.apiclient.wechat.user.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class UserInfoGetResponse extends WechatResponse {
	/****
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 */
	int subscribe;
	String openid;
	String nickname;
	int sex;
	String language = "zh_CN";
	String city;
	String province;
	String country;
	String headimgurl;
	@JsonProperty("subscribe_time")
	String subscribeTime;
	String unionid;
	String remark;
	int groupid;
	@JsonProperty("tagid_list")
	List<Integer> tagidList;
	@JsonProperty("subscribe_scene")
	String subscribeScene;
	@JsonProperty("qr_scene")
	int qrScene;
	@JsonProperty("qr_scene_str")
	String qrSceneStr;
}
