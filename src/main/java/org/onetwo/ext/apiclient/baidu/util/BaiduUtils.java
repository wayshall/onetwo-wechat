package org.onetwo.ext.apiclient.baidu.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.onetwo.common.exception.BaseException;
import org.onetwo.common.file.FileUtils;
import org.onetwo.common.utils.LangUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class BaiduUtils {
	
	private static final URLCodec URL_CODEC = new URLCodec();
	
	/****
	 * 图像数据，base64编码后进行urlencode，要求base64编码和urlencode后大小不超过4M，最短边至少15px，最长边最大4096px,支持jpg/jpeg/png/bmp格式
	 * @param resource
	 * @return
	 */
	public static String encodeImage(Resource resource) {
		try {
			InputStream input = resource.getInputStream();
			byte[] bytes = FileUtils.toByteArray(input);
//			byte[] res = URL_CODEC.encode(Base64.encodeBase64(bytes));
//			return LangUtils.newString(res);
			
			String base64 = LangUtils.newString(Base64.encodeBase64(bytes));
//			return LangUtils.encodeUrl(base64);
			return base64;
		} catch (IOException e) {
			throw new BaseException("to inputStream error: " + e.getMessage());
		}
	}

	public static String encodeImage(String filePath) {
		FileSystemResource res = new FileSystemResource(filePath);
		return encodeImage(res);
	}
	
	private BaiduUtils() {
	}

}
