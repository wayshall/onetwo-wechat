package org.onetwo.ext.apiclient.work.contact.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse.ExtattrData;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse.ExternalProfileData;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserInfoRequest {
	@NotNull
	@NotBlank
	private String userid;//": "zhangsan",
    private String name;//": "李四",
    private Long[] department;//": [1, 2],
    private Integer[] order;//": [1, 2],
    private String position; //": "后台工程师",
    private String mobile; //": "15913215421",
    private String gender; //": "1",
    private String email; //": "zhangsan@gzdev.com",
    @JsonProperty("is_leader_in_dept")
    private Integer[] isLeaderInDept; //": [1, 0],
//    private String avatar; //": "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0",
    @JsonProperty("avatar_mediaid")
    private String avatarMediaid;
    private String telephone; //": "020-123456",
    private Integer enable; //": 1,
    private String alias; //": "jackzhang",
    private String address; //": "广州市海珠区新港中路",
    private ExtattrData extattr;
    
    @JsonProperty("external_position")
    private String externalPosition;//": "产品经理",
    @JsonProperty("external_profile")
    private ExternalProfileData externalProfile;

}

