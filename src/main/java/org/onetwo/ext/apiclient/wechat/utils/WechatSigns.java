package org.onetwo.ext.apiclient.wechat.utils;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.md.CodeType;
import org.onetwo.common.md.Hashs;
import org.onetwo.common.md.MessageDigestHasher;
import org.onetwo.common.reflect.BeanToMapConvertor;
import org.onetwo.common.spring.rest.RestUtils;
import org.onetwo.common.spring.utils.EnhanceBeanToMapConvertor.EnhanceBeanToMapBuilder;
import org.onetwo.common.utils.Assert;
import org.onetwo.common.utils.ParamUtils;
import org.slf4j.Logger;
import org.springframework.util.MultiValueMap;

/**
 * @author weishao zeng
 * <br/>
 */
public abstract class WechatSigns {
	private static final Logger logger = JFishLoggerFactory.getLogger(WechatSigns.class);
	private static final MessageDigestHasher MD5 = Hashs.md5(false, CodeType.HEX);
	private static final BeanToMapConvertor BEAN_TO_MAP_CONVERTOR = EnhanceBeanToMapBuilder.enhanceBuilder()
																							.enableJsonPropertyAnnotation()
																							.enableFieldNameAnnotation()
//																							.enableUnderLineStyle()
																							.propertyAcceptor((p, v)->v!=null)
																							.excludeProperties("sign" 
//																									, "signType", "sign_type"
																									)
//																							.valueConvertor((p, v)->v.toString())
																							.build();

	public static String sign(String signKey, Object request){
		String sourceString = convertToSourceString(signKey, request);
		MessageDigestHasher hasher = MD5;//Hashs.md5(false, CodeType.HEX);
		String hashString = hasher.hash(sourceString).toUpperCase();
		if(logger.isDebugEnabled()){
//			logger.debug("source string: {}", sourceString);
			logger.debug("hash string: {}", hashString);
		}
		return hashString;
	}
	
	public static String convertToSourceString(String signKey, Object request){
		Assert.hasText(signKey, "signKey can not blank");
		MultiValueMap<String, Object> requestMap = RestUtils.toMultiValueMap(request, BEAN_TO_MAP_CONVERTOR);
		final String paramString = ParamUtils.comparableKeyMapToParamString(requestMap);
		String sourceString = paramString + "&key=" + signKey;
		if(logger.isDebugEnabled()){
			logger.debug("param string: {}", paramString);
			logger.debug("source string: {}", sourceString);
		}
		return sourceString;
	}
	
	public static boolean isSignCorrect(Object data, String signKey, String sign) {
		String signResult = sign(signKey, data);
		return signResult.equals(sign);
	}
	
	public static void checkSign(Object data, String signKey, String sign) {
		if(!isSignCorrect(data, signKey, sign)) {
			throw new WechatException("签名错误！");
		}
	}
}

