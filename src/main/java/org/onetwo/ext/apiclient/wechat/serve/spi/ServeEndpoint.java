package org.onetwo.ext.apiclient.wechat.serve.spi;

import org.onetwo.ext.apiclient.wechat.serve.dto.MessageParam;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MediaTypeKeys;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wayshall
 * <br/>
 */
public interface ServeEndpoint {

	@RequestMapping(path="/{clientId}", method=RequestMethod.GET, params="echostr")
	String auth(@PathVariable("clientId") String clientId, ServeAuthParam authRequet);

	@RequestMapping(path="/{clientId}", 
					method=RequestMethod.POST, 
					consumes={MediaTypeKeys.TEXT_XML_VALUE_UTF8, MediaTypeKeys.APPLICATION_XML_VALUE_UTF8},
					produces={MediaTypeKeys.TEXT_XML_VALUE_UTF8})
	Object onMessageReceived(@PathVariable("clientId") String clientId, MessageParam msgParam, String message);

}