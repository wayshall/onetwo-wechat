package org.onetwo.ext.apiclient.qcloud.api.auth;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.onetwo.common.exception.BaseException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.reflect.BeanToMapConvertor;
import org.onetwo.common.reflect.BeanToMapConvertor.ObjectPropertyContext;
import org.onetwo.common.spring.rest.RestUtils;
import org.onetwo.common.spring.utils.EnhanceBeanToMapConvertor.EnhanceBeanToMapBuilder;
import org.onetwo.common.spring.utils.EnhanceBeanToMapConvertor.JsonPropertyConvert;
import org.onetwo.common.utils.Assert;
import org.onetwo.common.utils.ParamUtils;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.slf4j.Logger;
import org.springframework.util.MultiValueMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author weishao zeng
 * <br/>
 */
public abstract class AuthSigns {
	private static final Logger logger = JFishLoggerFactory.getLogger(AuthSigns.class);
	
	@AllArgsConstructor
	public static enum AuthSignTypes {
		HmacSHA1("HmacSHA1"),
		HmacSHA256("HmacSHA256");
		
		@Getter
		private String name;
	}
			
//	private static final MessageDigestHasher MD5 = Hashs.md5(false, CodeType.HEX);
	private static final BeanToMapConvertor BEAN_TO_MAP_CONVERTOR = EnhanceBeanToMapBuilder.enhanceBuilder()
//																							.enableJsonPropertyAnnotation()
//																							.enableFieldNameAnnotation()
																							.propertyNameConvertor(new JsonPropertyConvert(true, true, false) {
																								@Override
																								public String convert(ObjectPropertyContext ctx) {
																									String name = super.convert(ctx);
																									name = name.replace('_', '.');
																									return name;
																								}
																							})
																							.propertyAcceptor((p, v)->v!=null)
																							.build();

	
	public static String signHmac(String signKey, SignableData signData){
		return signHmac(signKey, signData, AuthSignTypes.valueOf(signData.getRequest().getSignatureMethod()));
	}
	
	public static String signHmac(String signKey, SignableData signData, AuthSignTypes signTypes){
		String sourceString = convertToSourceString(signKey, signData);
		String hashString = null;
		try {
			String name = signTypes.getName();
			Mac sha256_HMAC = Mac.getInstance(name);
			SecretKeySpec secret_key = new SecretKeySpec(signKey.getBytes("UTF-8"), name);
			sha256_HMAC.init(secret_key);
			byte[] encodedBytes = sha256_HMAC.doFinal(sourceString.getBytes("UTF-8"));
//			hashString = Hex.encodeHexString(encodedBytes).toUpperCase();
			hashString = Base64.encodeBase64String(encodedBytes);
		} catch (Exception e) {
			throw new BaseException("签名出错: " + signData, e).putAsMap(signData);
		}
		if(logger.isDebugEnabled()){
//			logger.debug("source string: {}", sourceString);
			logger.debug("hash string: {}", hashString);
		}
		return hashString;
	}
	
	public static String convertToSourceString(String signKey, SignableData data){
		Assert.hasText(signKey, "signKey can not blank");
		AuthableRequest request = data.getRequest();
		MultiValueMap<String, Object> requestMap = RestUtils.toMultiValueMap(request, BEAN_TO_MAP_CONVERTOR);
		final String paramString = ParamUtils.comparableKeyMapToParamString(requestMap);
//		String sourceString = paramString + "&key=" + signKey;
		StringBuilder sourceString = new StringBuilder();
		/*if (StringUtils.isNotBlank(data.getHost())) {
			
		}*/
		sourceString.append(data.getMethod().toUpperCase())
					.append(data.getHost())
					.append(data.getPath())
					.append("?")
					.append(paramString);
		if(logger.isDebugEnabled()){
			logger.debug("param string: {}", paramString);
			logger.debug("source string: {}", sourceString);
		}
		return sourceString.toString();
	}
	
	public static boolean isSignCorrect(SignableData data, String signKey, String sign, AuthSignTypes signType) {
		String signResult = signHmac(signKey, data, signType);
		return signResult.equals(sign);
	}
	
	public static void checkSign(SignableData data, String signKey, String sign, AuthSignTypes signType) {
		String signResult = signHmac(signKey, data, signType);
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

