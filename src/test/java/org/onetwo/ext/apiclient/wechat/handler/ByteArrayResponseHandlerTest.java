package org.onetwo.ext.apiclient.wechat.handler;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ByteArrayResponseHandlerTest {
	
	@Test
	public void testExtraceName() {
		String disposition = "attachment; filename=\"MEDIA_ID.jpg\"";
		String filename = ByteArrayResponseHandler.extractFileName(disposition);
		System.out.println("filename: " + filename);
		assertThat(filename).isEqualTo("MEDIA_ID.jpg");
	}

}
