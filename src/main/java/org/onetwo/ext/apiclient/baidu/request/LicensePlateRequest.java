package org.onetwo.ext.apiclient.baidu.request;

import org.onetwo.common.annotation.FieldName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LicensePlateRequest extends BaiduBaseRequest {
	
	/****
	 * 和url二选一
	 * 图像数据，base64编码后进行urlencode，要求base64编码和urlencode后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/jpeg/png/bmp格式
	 */
	String image;
	
	/****
	 * 和image二选一
	 * 图片完整URL，URL长度不超过1024字节，URL对应的图片base64编码后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/jpeg/png/bmp格式，当image字段存在时url字段失效
请注意关闭URL防盗链
	 */
	String url;
	
	/****
	 * 是否检测多张车牌，默认为false，当置为true的时候可以对一张图片内的多张车牌进行识别
	 */
	@FieldName("multi_detect")
	String multiDetect;

	/****
	 * 在高拍等车牌较小的场景下可开启，默认为false，当置为true时，能够提高对较小车牌的检测和识别
	 */
	@FieldName("multi_scale")
	String multiScale;

}
