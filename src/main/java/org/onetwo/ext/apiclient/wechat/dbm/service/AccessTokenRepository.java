package org.onetwo.ext.apiclient.wechat.dbm.service;

import java.util.Optional;

import org.onetwo.common.db.spi.BaseEntityManager;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.dbm.entity.WxAccessTokenEntity;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author weishao zeng
 * <br/>
 */
@Transactional
public class AccessTokenRepository {
	
	@Autowired
	private BaseEntityManager baseEntityManager;
	
	@Transactional(readOnly=true)
	public Optional<AccessTokenInfo> findByAppid(AppidRequest appidRequest) {
		String id = getId(appidRequest);
		WxAccessTokenEntity at = baseEntityManager.findById(WxAccessTokenEntity.class, id);
		if (at==null) {
			return Optional.empty();
		}
		AccessTokenInfo atInfo = AccessTokenInfo.builder()
											.appid(appidRequest.getAppid())
											.accessToken(at.getAccessToken())
											.expiresIn(at.getExpiresIn())
											.updateAt(at.getUpdateAt())
											.build();
		return Optional.of(atInfo);
	}
	
	public WxAccessTokenEntity save(AccessTokenInfo atInfo, AppidRequest appidRequest) {
		String id = getId(appidRequest);
		WxAccessTokenEntity tokenEntity = new WxAccessTokenEntity();
		tokenEntity.setId(id);
		tokenEntity.setWxAppid(appidRequest.getAppid());
		tokenEntity.setAccessToken(atInfo.getAccessToken());
		tokenEntity.setExpiresIn(atInfo.getExpiresIn());
		baseEntityManager.save(tokenEntity);
		return tokenEntity;
	}
	
	public void removeByAppid(AppidRequest appidRequest) {
//		WxAccessTokenEntity at = baseEntityManager.findById(WxAccessTokenEntity.class, appid);
		String appid = getId(appidRequest);
		baseEntityManager.removeById(WxAccessTokenEntity.class, appid);
	}
	
	private String getId(AppidRequest appidRequest) {
		return WechatUtils.getAppidKey(appidRequest);
	}

}

