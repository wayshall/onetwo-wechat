package org.onetwo.ext.apiclient.qcloud.trtc;
/**
 * @author weishao zeng
 * <br/>
 */

import org.junit.Test;
import org.onetwo.ext.apiclient.qcloud.trtc.service.TrtcSignService;
import org.springframework.beans.factory.annotation.Autowired;

public class TrtcSignServiceTest extends QCloudTrtcBaseBootTests {
	
	@Autowired
	TrtcSignService trtcSignService;
	
	@Test
	public void testSign() {
		String sign = this.trtcSignService.genSig("way", 60L);
		System.out.println("sign: " + sign);
	}

}
