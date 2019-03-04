package org.onetwo.ext.apiclient.wechat.dbm.service;

import java.util.Optional;

import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.support.impl.AbstractAccessTokenService;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.springframework.util.Assert;

/**
 * 基于database
 * 
 * @author wayshall
 * <br/>
 */
public class DbStoreAccessTokenService extends AbstractAccessTokenService {
	
	private AccessTokenRepository accessTokenRepository;
	
	public DbStoreAccessTokenService(AccessTokenRepository accessTokenRepository) {
		super();
		this.accessTokenRepository = accessTokenRepository;
	}

	@Override
	protected String getStoreType() {
		return "database";
	}

	@Override
	protected void removeByAppid(String appid) {
		this.accessTokenRepository.removeByAppid(appid);
	}


	@Override
	protected void saveNewToken(AccessTokenInfo newToken) {
		this.accessTokenRepository.save(newToken);
	}


	public Optional<AccessTokenInfo> getAccessToken(String appid) {
		Assert.hasText(appid, "appid must have length; it must not be null or empty");
		Optional<AccessTokenInfo> at = accessTokenRepository.findByAppid(appid);
		if(logger.isDebugEnabled()){
			logger.debug("get accessToken from database server...");
		}
		return at;
	}
	@Override
	public AccessTokenInfo getOrRefreshAccessToken(GetAccessTokenRequest request) {
		Optional<AccessTokenInfo> atOpt = getAccessToken(request.getAppid());
		if(atOpt.isPresent() && !atOpt.get().isExpired()){
			return atOpt.get();
		}
		AccessTokenInfo at = refreshAccessToken(request);
		return at;
	}

}
