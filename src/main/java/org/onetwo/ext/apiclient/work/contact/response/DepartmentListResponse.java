package org.onetwo.ext.apiclient.work.contact.response;

import java.util.List;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper=true)
public class DepartmentListResponse extends WechatResponse {
	
	/***
	 * 部门列表数据
	 */
	private List<DepartmentData> department;
	
	@Data
	public static class DepartmentData {
		private Long id;//": 2,
        private String name; //": "广州研发中心",
        /***
         * 父亲部门id。根部门为1
         */
        private Long parentid; //": 1,
        /***
         * 在父部门中的次序值。order值大的排序靠前。值范围是[0, 2^32)
         */
        private Integer order; //": 10
	}

	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class CreateDepartmentResponse extends WechatResponse {
		private Long id;//": 2,
	}
}

