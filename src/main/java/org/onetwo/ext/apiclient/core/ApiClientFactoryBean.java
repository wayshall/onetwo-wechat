package org.onetwo.ext.apiclient.core;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.common.utils.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.netflix.feign.FeignContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;

import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.Target.HardCodedTarget;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApiClientFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {

	private static final Targeter targeter;

	static {
		Targeter targeterToUse;
		if (ClassUtils.isPresent("feign.hystrix.HystrixFeign",
				ApiClientFactoryBean.class.getClassLoader())) {
			targeterToUse = new HystrixTargeter();
		}
		else {
			targeterToUse = new DefaultTargeter();
		}
		targeter = targeterToUse;
	}

	private Class<?> type;
	
	private String name;

	private String url;

	private String path;

	private boolean decode404;

	private ApplicationContext applicationContext;

	private Class<?> fallback = void.class;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(StringUtils.isBlank(name)){
			name = type.getName();
		}
	}

	protected Feign.Builder feign(FeignContext context) {
		Logger logger = getOptional(context, Logger.class);

		if (logger == null) {
			logger = new Slf4jLogger(this.type);
		}

		// @formatter:off
		Feign.Builder builder = get(context, Feign.Builder.class)
				// required values
				.logger(logger)
				.encoder(get(context, Encoder.class))
				.decoder(get(context, Decoder.class))
				.contract(get(context, Contract.class));
		// @formatter:on

		// optional values
		Logger.Level level = getOptional(context, Logger.Level.class);
		if (level != null) {
			builder.logLevel(level);
		}
		Retryer retryer = getOptional(context, Retryer.class);
		if (retryer != null) {
			builder.retryer(retryer);
		}
		ErrorDecoder errorDecoder = getOptional(context, ErrorDecoder.class);
		if (errorDecoder != null) {
			builder.errorDecoder(errorDecoder);
		}
		Request.Options options = getOptional(context, Request.Options.class);
		if (options != null) {
			builder.options(options);
		}
		Map<String, RequestInterceptor> requestInterceptors = context.getInstances(
				this.name, RequestInterceptor.class);
		if (requestInterceptors != null) {
			builder.requestInterceptors(requestInterceptors.values());
		}

		if (decode404) {
			builder.decode404();
		}

		return builder;
	}

	protected <T> T get(FeignContext context, Class<T> type) {
		T instance = context.getInstance(this.name, type);
		if (instance == null) {
			throw new IllegalStateException("No bean found of type " + type + " for "
					+ this.name);
		}
		return instance;
	}

	protected <T> T getOptional(FeignContext context, Class<T> type) {
		return context.getInstance(this.name, type);
	}

	protected <T> T loadBalance(Feign.Builder builder, FeignContext context,
			HardCodedTarget<T> target) {
		Client client = getOptional(context, Client.class);
		if (client != null) {
			builder.client(client);
			return targeter.target(this, builder, context, target);
		}

		throw new IllegalStateException(
				"No Feign Client for loadBalancing defined. Did you forget to include spring-cloud-starter-ribbon?");
	}

	@Override
	public Object getObject() throws Exception {
		FeignContext context = applicationContext.getBean(FeignContext.class);
		Feign.Builder builder = feign(context);

		if (!StringUtils.hasText(this.url)) {
			String url;
			if (!this.name.startsWith("http")) {
				url = "http://" + this.name;
			}
			else {
				url = this.name;
			}
			url += cleanPath();
			return loadBalance(builder, context, new HardCodedTarget<>(this.type,
					this.name, url));
		}
		if (StringUtils.hasText(this.url) && !this.url.startsWith("http")) {
			this.url = "http://" + this.url;
		}
		String url = this.url + cleanPath();
		return targeter.target(this, builder, context, new HardCodedTarget<>(
				this.type, this.name, url));
	}

	private String cleanPath() {
		String path = this.path.trim();
		if (StringUtils.hasLength(path)) {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (path.endsWith("/")) {
				path = path.substring(0, path.length() - 1);
			}
		}
		return path;
	}

	@Override
	public Class<?> getObjectType() {
		return type;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	interface Targeter {
		<T> T target(ApiClientFactoryBean factory, Feign.Builder feign, FeignContext context,
				HardCodedTarget<T> target);
	}

	static class DefaultTargeter implements Targeter {

		@Override
		public <T> T target(ApiClientFactoryBean factory, Feign.Builder feign, FeignContext context,
							HardCodedTarget<T> target) {
			return feign.target(target);
		}
	}

	@SuppressWarnings("unchecked")
	static class HystrixTargeter implements Targeter {

		@Override
		public <T> T target(ApiClientFactoryBean factory, Feign.Builder feign, FeignContext context,
							HardCodedTarget<T> target) {
			if (factory.fallback == void.class
					|| !(feign instanceof feign.hystrix.HystrixFeign.Builder)) {
				return feign.target(target);
			}

			Object fallbackInstance = context.getInstance(factory.name, factory.fallback);
			if (fallbackInstance == null) {
				throw new IllegalStateException(String.format(
						"No fallback instance of type %s found for feign client %s",
						factory.fallback, factory.name));
			}

			if (!target.type().isAssignableFrom(factory.fallback)) {
				throw new IllegalStateException(
						String.format(
								"Incompatible fallback instance. Fallback of type %s is not assignable to %s for feign client %s",
								factory.fallback, target.type(), factory.name));
			}

			feign.hystrix.HystrixFeign.Builder builder = (feign.hystrix.HystrixFeign.Builder) feign;
			return builder.target(target, (T) fallbackInstance);
		}
	}

}
