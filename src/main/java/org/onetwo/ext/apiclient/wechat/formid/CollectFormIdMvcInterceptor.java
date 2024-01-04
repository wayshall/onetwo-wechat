package org.onetwo.ext.apiclient.wechat.formid;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.boot.core.web.mvc.interceptor.MvcInterceptorAdapter;
import org.onetwo.common.web.userdetails.GenericUserDetail;
import org.onetwo.common.web.userdetails.SessionUserManager;
import org.onetwo.ext.apiclient.wechat.event.WechatEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import lombok.extern.slf4j.Slf4j;

/**
 * 小程序formid收集拦截器
 * @author weishao zeng
 * @deprecated 小程序已废弃formid机制 
 * <br/>
 */
@Slf4j
@Deprecated
public class CollectFormIdMvcInterceptor extends MvcInterceptorAdapter {
	
	private static final String FORM_ID = "collectFormId";
	@Autowired
	private SessionUserManager<GenericUserDetail<?>> sessionUserManager;
	@Autowired
	private WechatEventBus wechatEventBus;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
		try {
			collectWxformId(request);
		} catch (Exception e) {
			log.error("collect wechat formId error.", e);
		}
		
		return true;
	}
	

	protected void collectWxformId(HttpServletRequest request) {
		String formId = request.getParameter(FORM_ID);
		if (StringUtils.isBlank(formId)) {
			return ;
		}
		
		GenericUserDetail<?> userDetail = sessionUserManager.getCurrentUser();
		CollectFormIdEvent collectFormIdEvent = new CollectFormIdEvent(userDetail, formId, System.currentTimeMillis());
		this.wechatEventBus.post(collectFormIdEvent);
	}
	
}

