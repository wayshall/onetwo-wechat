package org.onetwo.ext.apiclient.work.crypt;

import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;

/**
 * @author weishao zeng
 * <br/>
 */
public class WXBizMsgCryptAdaptor implements WechatMsgCrypt {
	
	private WXBizMsgCrypt wxBizMsgCrypt;
	
	public WXBizMsgCryptAdaptor(String token, String encodingAesKey, String receiveid) {
		this.wxBizMsgCrypt = new WXBizMsgCrypt(token, encodingAesKey, receiveid);
	}
	@Override
	public String encryptMsg(String replyMsg, String timeStamp, String nonce) {
		return wxBizMsgCrypt.EncryptMsg(replyMsg, timeStamp, nonce);
	}

	@Override
	public String decryptBody(String msgSignature, String timeStamp, String nonce, String postData) {
		return wxBizMsgCrypt.DecryptMsg(msgSignature, timeStamp, nonce, postData);
	}

	@Override
	public String decryptMsg(String msgSignature, String timeStamp, String nonce, String encrypt) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String verifyUrl(String msgSignature, String timeStamp, String nonce, String echoStr) {
		return wxBizMsgCrypt.VerifyURL(msgSignature, timeStamp, nonce, echoStr);
	}

}

