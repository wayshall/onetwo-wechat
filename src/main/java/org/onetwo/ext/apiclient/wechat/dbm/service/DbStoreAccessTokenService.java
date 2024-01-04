package org.onetwo.ext.apiclient.wechat.dbm.service;

import java.util.Optional;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.support.impl.AbstractAccessTokenService;
import org.onetwo.common.utils.Assert;

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
	protected void removeByAppid(AppidRequest appidRequest) {
		this.accessTokenRepository.removeByAppid(appidRequest);
	}


	@Override
	protected void saveNewToken(AccessTokenInfo newToken, AppidRequest appidRequest) {
		this.accessTokenRepository.save(newToken, appidRequest);
	}


	public Optional<AccessTokenInfo> getAccessToken(AppidRequest appidRequest) {
		Assert.hasText(appidRequest.getAppid(), "appid must have length; it must not be null or empty");
		Optional<AccessTokenInfo> at = accessTokenRepository.findByAppid(appidRequest);
		if(logger.isDebugEnabled()){
			logger.debug("get accessToken from database server...");
		}
		return at;
	}
	@Override
	public AccessTokenInfo getOrRefreshAccessToken(GetAccessTokenRequest request) {
		AppidRequest appidRequest = new AppidRequest(request.getAppid(), request.getAgentId(), request.getAccessTokenType());
		Optional<AccessTokenInfo> atOpt = getAccessToken(appidRequest);
		if(atOpt.isPresent() && !atOpt.get().isExpired()){
			return atOpt.get();
		}
		AccessTokenInfo at = refreshAccessToken(request);
		return at;
	}

}
