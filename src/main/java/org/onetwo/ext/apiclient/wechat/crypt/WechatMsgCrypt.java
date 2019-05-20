package org.onetwo.ext.apiclient.wechat.crypt;

import org.onetwo.common.log.JFishLoggerFactory;

/**
 * 抽取微信解密接口
 * 因为公众号提供的解密包和企业微信提供的解密包方法稍有不同，所以抽取一个接口，以适配不同场景的解密
 * 
 * Illegal key size异常查看：
 * https://work.weixin.qq.com/api/doc#90000/90138/90307/java%E5%BA%93
 * 异常java.security.InvalidKeyException:illegal Key Size的解决方案：在官方网站下载JCE无限制权限策略文件（请到官网下载对应的版本， 例如JDK7的下载地址：http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html )：下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt。
如果安装了JRE，将两个jar文件放到%JRE_HOME% \lib\security目录下覆盖原来的文件，如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件。

 * @author weishao zeng
 * <br/>
 */
public interface WechatMsgCrypt {
	
	default void logError(Throwable e) {
		JFishLoggerFactory.getLogger(WechatMsgCrypt.class).error("wechat crypt error：" + e.getMessage(), e);
	}

	/**
	 * 将公众平台回复用户的消息加密打包.
	 * <ol>
	 * 	<li>对要发送的消息进行AES-CBC加密</li>
	 * 	<li>生成安全签名</li>
	 * 	<li>将消息密文和安全签名打包成xml格式</li>
	 * </ol>
	 * 
	 * @param replyMsg 公众平台待回复用户的消息，xml格式的字符串
	 * @param timeStamp 时间戳，可以自己生成，也可以用URL参数的timestamp
	 * @param nonce 随机串，可以自己生成，也可以用URL参数的nonce
	 * 
	 * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce, encrypt的xml格式的字符串
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	String encryptMsg(String replyMsg, String timeStamp, String nonce) ;

	/**
	 * 检验消息的真实性，并且获取解密后的明文.
	 * <ol>
	 * 	<li>利用收到的密文生成安全签名，进行签名验证</li>
	 * 	<li>若验证通过，则提取xml中的加密消息</li>
	 * 	<li>对消息进行解密</li>
	 * </ol>
	 * 
	 * @param msgSignature 签名串，对应URL参数的msg_signature
	 * @param timeStamp 时间戳，对应URL参数的timestamp
	 * @param nonce 随机串，对应URL参数的nonce
	 * @param postData 密文，对应POST请求的数据
	 * 
	 * @return 解密后的原文
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	default String decryptBody(String msgSignature, String timeStamp, String nonce, String postData) {
		Object[] encrypt = XMLParse.extract(postData);
		return decryptMsg(msgSignature, timeStamp, nonce, encrypt[1].toString());
	}

	String decryptMsg(String msgSignature, String timeStamp, String nonce, String encrypt) ;

	/**
	 * 验证URL
	 * @param msgSignature 签名串，对应URL参数的msg_signature
	 * @param timeStamp 时间戳，对应URL参数的timestamp
	 * @param nonce 随机串，对应URL参数的nonce
	 * @param echoStr 随机串，对应URL参数的echostr
	 * 
	 * @return 解密之后的echostr
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	String verifyUrl(String msgSignature, String timeStamp, String nonce, String echoStr) ;

}
