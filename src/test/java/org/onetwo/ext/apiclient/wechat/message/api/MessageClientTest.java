package org.onetwo.ext.apiclient.wechat.message.api;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.material.response.SendAllResponse;
import org.onetwo.ext.apiclient.wechat.message.request.MpnewsRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class MessageClientTest extends WechatBaseTestsAdapter {

	@Autowired
	MessageClient messageClient;
	
    /**
     * 根据标签进行群发
     */
    @Test
    public void testSendAll(){
        MpnewsRequest mpNews = new MpnewsRequest();
        mpNews.getMpnews().setMediaId("kQiTKiCLHlfN6aKTMJ-IP7yYyxVx1qA61BfJ1fdAd2g");
        mpNews.setSendIgnoreReprint(0);
        mpNews.setFilterData(false, 2);

        SendAllResponse resBody = messageClient.sendAll(accessTokenInfo, mpNews);
        System.out.println("res:"+resBody);
    }
    
}
