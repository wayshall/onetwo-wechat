package org.onetwo.ext.apiclient.qcloud.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
public class LivingResult implements Serializable {
	
	String pushUrl;
	String playRtmp;
	String playFlv;
	String playHls;

}
