package org.onetwo.ext.apiclient.wechat.serve.spi;

import java.util.Map;

import org.onetwo.ext.apiclient.wechat.serve.dto.MessageParam;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MediaTypeKeys;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wayshall
 * <br/>
 */
public interface ServeEndpoint {

	@RequestMapping(method=RequestMethod.GET, params="echostr")
	String auth(ServeAuthParam authRequet);

	@RequestMapping(method=RequestMethod.POST, 
					consumes={MediaTypeKeys.TEXT_XML_VALUE_UTF8, MediaTypeKeys.APPLICATION_XML_VALUE_UTF8},
					produces={MediaTypeKeys.TEXT_XML_VALUE_UTF8})
	Object onMessageReceived(MessageParam msgParam, Map<String, Object> message);

}