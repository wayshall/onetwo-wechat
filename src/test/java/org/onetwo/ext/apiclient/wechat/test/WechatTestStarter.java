package org.onetwo.ext.apiclient.wechat.test;

import org.onetwo.boot.module.redis.RedisConfiguration;
import org.onetwo.ext.apiclient.wechat.EnableWechatClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author wayshall
 * <br/>
 */

@SpringBootApplication
@EnableWechatClient
@Import(RedisConfiguration.class)
public class WechatTestStarter {

}
