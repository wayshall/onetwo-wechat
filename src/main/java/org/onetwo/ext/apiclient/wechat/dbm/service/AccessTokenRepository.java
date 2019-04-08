package org.onetwo.ext.apiclient.wechat.dbm.service;

import java.util.Optional;

import org.onetwo.common.db.spi.BaseEntityManager;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.dbm.entity.WxAccessTokenEntity;
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
	public Optional<AccessTokenInfo> findByAppid(String appid) {
		WxAccessTokenEntity at = baseEntityManager.findById(WxAccessTokenEntity.class, appid);
		if (at==null) {
			return Optional.empty();
		}
		AccessTokenInfo atInfo = AccessTokenInfo.builder()
											.appid(appid)
											.accessToken(at.getAccessToken())
											.expiresIn(at.getExpiresIn())
											.updateAt(at.getUpdateAt())
											.build();
		return Optional.of(atInfo);
	}
	
	public WxAccessTokenEntity save(AccessTokenInfo atInfo) {
		WxAccessTokenEntity tokenEntity = new WxAccessTokenEntity();
		tokenEntity.setWxAppid(atInfo.getAppid());
		tokenEntity.setAccessToken(atInfo.getAccessToken());
		tokenEntity.setExpiresIn(atInfo.getExpiresIn());
		baseEntityManager.save(tokenEntity);
		return tokenEntity;
	}
	
	public void removeByAppid(String appid) {
//		WxAccessTokenEntity at = baseEntityManager.findById(WxAccessTokenEntity.class, appid);
		baseEntityManager.removeById(WxAccessTokenEntity.class, appid);
	}

}

