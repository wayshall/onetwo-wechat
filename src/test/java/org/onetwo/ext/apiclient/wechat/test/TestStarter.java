package org.onetwo.ext.apiclient.wechat.test;

import org.onetwo.boot.module.redis.RedisConfiguration;
import org.onetwo.ext.apiclient.wechat.EnableWechatClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author LeeKITMAN
 */
@SpringBootApplication
@EnableWechatClient(enableMessageServe = false)
@Import(RedisConfiguration.class)
public class TestStarter {
}
