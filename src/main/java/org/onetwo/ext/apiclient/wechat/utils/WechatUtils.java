package org.onetwo.ext.apiclient.wechat.utils;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Date;

import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.encrypt.AESCoder;
import org.onetwo.common.encrypt.Crypts;
import org.onetwo.common.encrypt.PKCS7Encoder;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.exception.BaseException;
import org.onetwo.common.exception.ErrorTypes;
import org.onetwo.common.exception.ServiceException;
import org.onetwo.common.jackson.JsonMapper;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.basic.api.TokenApi;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.crypt.AesException;
import org.onetwo.ext.apiclient.wechat.crypt.WXBizMsgCrypt;
import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.GrantTypeKeys;
import org.onetwo.ext.apiclient.wechat.wxa.response.WxappUserInfo;
import org.onetwo.ext.apiclient.work.crypt.WXBizMsgCryptAdaptor;
import org.springframework.http.ResponseEntity;

/**
 * @author wayshall
 * <br/>
 */
public class WechatUtils {

//	public static final String ACCESS_TOKEN_PREFIX = "WX_ACCESSTOKEN";
//	public static final String KEY_SPLITOR = ":";
	public static final String LOCK_KEY = "LOCKER:WX_ACESSTOKEN:";
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public static void assertWechatConfigNotNull(WechatConfig wechatConfig, String appid) {
		if (wechatConfig==null) {
			throw new WechatException("can not find wechat config for appid: " + appid);
		}
	}
	public static WxappUserInfo decrypt(String sessionKey, String iv, String encryptedData){
		try {
			return decrypt0(sessionKey, iv, encryptedData);
		} catch (Exception e) {
			throw new ServiceException("解密错误", e).put("iv", iv)
													.put("encryptedData", encryptedData);
		}
	}
	
	public static WxappUserInfo decrypt0(String sessionKey, String iv, String encryptedData){
		if (StringUtils.isBlank(encryptedData)) {
			return null;
		}
		AESCoder aes = AESCoder.pkcs7Padding(Base64.decodeBase64(sessionKey))
								.initer((cipher, mode, keySpec)->{
									AlgorithmParameters params = AlgorithmParameters.getInstance(Crypts.AES_KEY);  
							        params.init(new IvParameterSpec(Base64.decodeBase64(iv)));
							        cipher.init(mode, keySpec, params);
								});
		byte[] decryptData = aes.decrypt(Base64.decodeBase64(encryptedData));
		String userInfo = LangUtils.newString(PKCS7Encoder.decode(decryptData));
		WxappUserInfo wxUser = JsonMapper.defaultMapper().fromJson(userInfo, WxappUserInfo.class);
		return wxUser;
	}
	
	public static WechatMobileVO decryptMobile(String sessionKey, String iv, String encryptedData){
		try {
			return decrypt0(sessionKey, iv, encryptedData, WechatMobileVO.class);
		} catch (Exception e) {
			throw new ServiceException("手机号码解密错误", e).put("iv", iv)
												.put("encryptedData", encryptedData)
												.put("sessionKey", sessionKey);
		}
	}
	
	public static <T> T decrypt(String sessionKey, String iv, String encryptedData, Class<T> messageType){
		try {
			return decrypt0(sessionKey, iv, encryptedData, messageType);
		} catch (Exception e) {
			throw new ServiceException("解密错误", e).put("iv", iv)
												.put("encryptedData", encryptedData)
												.put("sessionKey", sessionKey);
		}
	}
	
	public static <T> T decrypt0(String sessionKey, String iv, String encryptedData, Class<T> messageType){
		if (StringUtils.isBlank(encryptedData)) {
			return null;
		}
		AESCoder aes = AESCoder.pkcs7Padding(Base64.decodeBase64(sessionKey))
								.initer((cipher, mode, keySpec)->{
									AlgorithmParameters params = AlgorithmParameters.getInstance(Crypts.AES_KEY);  
							        params.init(new IvParameterSpec(Base64.decodeBase64(iv)));
							        cipher.init(mode, keySpec, params);
								});
		byte[] decryptData = aes.decrypt(Base64.decodeBase64(encryptedData));
		String rawMessage = LangUtils.newString(PKCS7Encoder.decode(decryptData));
		T message = JsonMapper.defaultMapper().fromJson(rawMessage, messageType);
		return message;
	}
	
	public static GetAccessTokenRequest createGetAccessTokenRequest(WechatAppInfo wechatAppInfo){
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
															.grantType(GrantTypeKeys.CLIENT_CREDENTIAL)
															.appid(wechatAppInfo.getAppid())
															.secret(wechatAppInfo.getAppsecret())
															.build();
		return request;
	}
	
	/*public static GetAccessTokenRequest createGetAccessTokenRequest(WechatConfig wechatConfig){
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
				.grantType(wechatConfig.getGrantType())
				.appid(wechatConfig.getAppid())
				.secret(wechatConfig.getAppsecret())
				.build();
		return request;
	}*/
	
	public static AccessTokenInfo getAccessToken(TokenApi wechatServer, GetAccessTokenRequest request){
		AccessTokenResponse response = wechatServer.getAccessToken(request);
		return toAccessTokenInfo(request.getAppid(), response);
	}
	
	public static AccessTokenInfo toAccessTokenInfo(String appid, AccessTokenResponse response){
		AccessTokenInfo accessToken = AccessTokenInfo.builder()
													.accessToken(response.getAccessToken())
													.expiresIn(response.getExpiresIn())
													.appid(appid)
													.updateAt(new Date())
													.expireAt(response.getExpireAt())
													.build();
		return accessToken;
	}

	/*public static String getAccessTokenKey(String appid, AccessTokenTypes accessTokenType){
		return WechatUtils.ACCESS_TOKEN_PREFIX + getAppidKey(appid, accessTokenType);
	}*/
	
	public static ApiClientException translateToApiClientException(ApiClientMethod invokeMethod, WechatResponse baseResponse, ResponseEntity<?> responseEntity){
		return WechatErrors.byErrcode(baseResponse.getErrcode())
						 .map(err-> {
							 JFishLoggerFactory.getCommonLogger().error("invoke wechat api error, errcode: {}, errmsg: {}", 
									 					baseResponse.getErrcode(), 
									 					baseResponse.getErrmsg());
							 ApiClientException apie = new ApiClientException(err, invokeMethod.getMethod(), null);
//							 apie.put("errmsg", baseResponse.getErrmsg());
							 return apie;
						 })
						 .orElse(new ApiClientException(ErrorTypes.of(baseResponse.getErrcode().toString(), 
								 										baseResponse.getErrmsg(), 
								 										responseEntity.getStatusCodeValue())
								 									));
	}
	
	/***
	 * WX_ACCESSTOKEN:wechat:appid
	 * WX_ACCESSTOKEN:wechat:appid:agentId
	 * @author weishao zeng
	 * @param appidRequest
	 * @return
	 
	public static String getAppidKey(AppidRequest appidRequest) {
//		return getAppidKey(appidRequest.getAppid(), appidRequest.getAccessTokenType());
		StringBuilder key = new StringBuilder();
		key.append(ACCESS_TOKEN_PREFIX).append(KEY_SPLITOR)
			.append(appidRequest.getAccessTokenType().name()).append(KEY_SPLITOR)
			.append(appidRequest.getAppid());
		if (appidRequest.getAgentId()!=null) {
			key.append(KEY_SPLITOR).append(appidRequest.getAgentId());
		}
		return key.toString();
	}*/
	
	/*private static String getAppidKey(String appid, AccessTokenTypes accessTokenType) {
		return appid + ":" + accessTokenType.name();
	}*/
	
	public static WechatMsgCrypt createWXBizMsgCrypt(WechatConfig wechatConfig) {
		if (wechatConfig==null) {
			throw new IllegalArgumentException("wechat config can not be null!");
		}
		try {
			WechatMsgCrypt wxbizMsgCrypt = null;
			if (!wechatConfig.isWorkWechat()) { //普通微信
				wxbizMsgCrypt = new WXBizMsgCrypt(wechatConfig.getToken(), wechatConfig.getEncodingAESKey(), wechatConfig.getAppid());
			} else { // 企业微信
				wxbizMsgCrypt = new WXBizMsgCryptAdaptor(wechatConfig.getToken(), wechatConfig.getEncodingAESKey(), wechatConfig.getAppid());
			}
			return wxbizMsgCrypt;
		} catch (AesException e) {
			throw new BaseException(e.getMessage(), e);
		}
	}
	
	private WechatUtils(){
	}

}
