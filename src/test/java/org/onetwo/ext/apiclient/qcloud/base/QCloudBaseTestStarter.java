package org.onetwo.ext.apiclient.qcloud.base;

import org.onetwo.ext.apiclient.qcloud.EnableQCloudService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author weishao zeng
 * <br/>
 */

@SpringBootApplication
@EnableQCloudService(nlp=true)
@EnableRetry(proxyTargetClass=true)
public class QCloudBaseTestStarter {

}
