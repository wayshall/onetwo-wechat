package org.onetwo.ext.apiclient.work.contact.util;

import java.util.List;
import java.util.Optional;

import org.onetwo.common.convert.Types;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse.Attribute;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse.AttributeType;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse.ExtattrData;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse.TextValue;
import org.springframework.beans.BeanWrapper;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
public interface ExtAttrAccessor {
	
	public static ExtAttrAccessor wrap(ExtattrData extattr) {
		return new DefaultExtAttrAccessor(extattr);
	}
	
	ExtattrData getExtattr();

    default <T> Optional<T> getExtattrTextValueAs(String name, Class<T> type) {
    	return getExtattrValue(name, TextValue.class).map(text -> Types.convertValue(text.getValue(), type));
    }
    
    default Optional<String> getExtattrTextValue(String name) {
    	return getExtattrValue(name, TextValue.class).map(text -> text.getValue());
    }
    
    default <T> Optional<T> getExtattrValue(String name, Class<T> attrType) {
    	return getExtattrValue(name, attrType, AttributeType.findByType(attrType));
    }
    
    default <T> Optional<T> getExtattrValue(String name, Class<T> attrType, AttributeType type) {
    	return getExtattr(name).filter(attr -> attr.getType().equals(type)).map(attr -> {
    		BeanWrapper bw = SpringUtils.newBeanWrapper(attr);
    		Object val = bw.getPropertyValue(attr.getType().name().toLowerCase());
    		return attrType.cast(val);
    	});
    }
    
    default Optional<Attribute> getExtattr(String name) {
    	ExtattrData extattr = getExtattr();
    	if (extattr==null || extattr.getAttrs()==null) {
    		return Optional.empty();
    	}
    	return extattr.getAttrs().stream().filter(attr ->  {
    		return attr.getName().equals(name);
    	})
    	.findFirst();
    }
    
    @Data
    public class DefaultExtAttrAccessor implements ExtAttrAccessor {
    	final private ExtattrData extattr;

		public DefaultExtAttrAccessor(ExtattrData extattr) {
			super();
			this.extattr = extattr;
		}

		public DefaultExtAttrAccessor(List<Attribute> attrs) {
			super();
			this.extattr = new ExtattrData();
			this.extattr.setAttrs(attrs);
		}
    	
    }
}
