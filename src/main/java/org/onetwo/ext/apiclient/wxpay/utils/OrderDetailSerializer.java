package org.onetwo.ext.apiclient.wxpay.utils;

import java.io.IOException;

import org.onetwo.common.jackson.JsonMapper;
import org.onetwo.ext.apiclient.wxpay.vo.request.UnifiedOrderRequest.OrderDetail;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author weishao zeng
 * <br/>
 */
public class OrderDetailSerializer extends JsonSerializer<OrderDetail> {

	private JsonMapper jsonMapper = JsonMapper.IGNORE_EMPTY;
	
	@Override
	public void serialize(OrderDetail value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		String data = jsonMapper.toJson(value);
		gen.writeString(data);
	}

}

