package org.onetwo.ext.apiclient.wechat.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.exception.BaseException;
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
//																							.propertyAcceptor((p, v)->v!=null)
																							.ignoreNull()
																							.excludeProperties("sign", 
																									"success"
//																									, "signType", "sign_type"
																									)
//																							.valueConvertor((p, v)->v.toString())
																							.build();

	public static String signMd5(String signKey, Object request){
		String sourceString = convertToSourceString(signKey, request);
		MessageDigestHasher hasher = MD5;//Hashs.md5(false, CodeType.HEX);
		String hashString = hasher.hash(sourceString).toUpperCase();
		if(logger.isDebugEnabled()){
//			logger.debug("source string: {}", sourceString);
			logger.debug("hash string: {}", hashString);
		}
		return hashString;
	}
	
	public static String signHmacSHA256(String signKey, Object request){
		String sourceString = convertToSourceString(signKey, request);
		String hashString = null;
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(signKey.getBytes("UTF-8"), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			hashString = Hex.encodeHexString(sha256_HMAC.doFinal(sourceString.getBytes("UTF-8"))).toUpperCase();
		} catch (Exception e) {
			throw new BaseException("签名出错: " + request, e).putAsMap(request);
		}
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
	
	public static String sign(Object data, String signKey, String signType) {
		String signResult = null;
		if (StringUtils.isBlank(signType) || WechatConstants.SIGN_MD5.equalsIgnoreCase(signType)) {
			signResult = signMd5(signKey, data);
		} else if (WechatConstants.SIGN_HMAC_SHA256.equalsIgnoreCase(signType)) {
			signResult = signHmacSHA256(signKey, data);
		}
		return signResult;
	}
	
	public static boolean isSignCorrect(Object data, String signKey, String sign, String signType) {
		String signResult = sign(data, signKey, signType);
		return signResult.equals(sign);
	}
	
	public static void checkSign(Object data, String signKey, String sign, String signType) {
		String signResult = sign(data, signKey, signType);
		if(logger.isDebugEnabled()){
			logger.debug("sign: {}", sign);
			logger.debug("signResult: {}", signResult);
		}
		if(!signResult.equals(sign)) {
			throw new WechatException("签名验证错误！").put("sign", sign)
												.put("signResult", signResult)
												.put("signType", signType);
		}
	}
}

