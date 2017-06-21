package org.onetwo.ext.apiclient.wechat.serve.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.md.Hashs;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;
import org.onetwo.ext.apiclient.wechat.serve.spi.BaseServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wayshall
 * <br/>
 */
@Service
public class BaseServeServiceImpl implements BaseServeService {
	
	@Autowired
	private WechatConfig wechatConfig;
	
	
	@Override
	public String auth(ServeAuthParam auth){
		return isValidRequest(auth)?auth.getEchostr():null;
	}
	
	private boolean isValidRequest(ServeAuthParam auth){
		List<String> authItems = new ArrayList<>();
		authItems.add(wechatConfig.getToken());
		authItems.add(auth.getTimestamp());
		authItems.add(auth.getNonce());
		Collections.sort(authItems);
		String source = StringUtils.join(authItems, "");
		String sha1String = Hashs.SHA.hash(source);
		return sha1String.equalsIgnoreCase(auth.getSignature());
	}

}
