package org.onetwo.ext.apiclient.qcloud.util;

import java.util.stream.Stream;

import org.onetwo.common.exception.ErrorType;

/**
 * @author wayshall
 * <br/>
 */
public class LiveErrors {

	public static enum CutoffErrors implements ErrorType {

		DELETE_STREAM(1, "[recv rtmp deleteStream]主播端主动断流"),
		CLOSE_STREAM(2, "[recv rtmp closeStream]主播端主动断流"),
		CLOSE_TCP(3, "[recv() return 0]主播端主动断开 TCP 连接"),
		ERROR_TCP(4, "[recv() return error]主播端 TCP 连接异常"),
		MESSAGE_TOO_LARGE(7, "[rtmp message large than 1M]收到流数据异常"),
		URL_INVALID(18, "[push url maybe invalid]推流鉴权失败，服务端禁止推流"),
		AUTH_FAILED(19, "[3rdparty auth failed]第三方鉴权失败，服务端禁止推流"),
		/***
		 * 其他错误码
		 */
		OTHER(-1, "[直播服务内部异常]如需处理请联系腾讯商务人员或者 提交工单，联系电话：4009-100-100"),
		;

        final private int value;
        final private String message;

        private CutoffErrors(int value, String message) {
        	this.value = value;
            this.message = message;
        }

        public int getValue() {
			return value;
		}

		public String getMessage() {
			return message;
		}

		@Override
        public String getErrorCode() {
            return name();
        }

        public String getErrorMessage() {
            return message;
        }

        public static CutoffErrors of(String name) {
            return Stream.of(values()).filter(s -> s.name().equalsIgnoreCase(name)).findAny()
                    .orElseThrow(() -> new IllegalArgumentException("error status: " + name));
        }

        public static CutoffErrors of(int value) {
            return Stream.of(values()).filter(s -> s.value==value).findAny()
                    .orElseThrow(() -> new IllegalArgumentException("error value: " + value));
        }
	}
}
