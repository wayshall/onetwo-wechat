package org.onetwo.ext.apiclient.wechat.serve;

import org.onetwo.ext.apiclient.wechat.serve.service.impl.BaseServeServiceImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ComponentScan(basePackageClasses=BaseServeServiceImpl.class)
public class WechatServeConfiguration {

}
