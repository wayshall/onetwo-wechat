package org.onetwo.ext.apiclient.work;

import org.onetwo.boot.module.redis.RedisConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author wayshall
 * <br/>
 */

@SpringBootApplication
@EnableWorkWechatClient
@Import(RedisConfiguration.class)
public class WorkWechatTestStarter {

}
