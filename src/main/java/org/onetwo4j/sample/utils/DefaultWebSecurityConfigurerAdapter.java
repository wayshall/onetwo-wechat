package org.onetwo4j.sample.utils;

import org.onetwo.boot.module.security.BootSecurityConfig;
import org.onetwo.ext.security.ajax.AjaxAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter.XFrameOptionsMode;

public class DefaultWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter  {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired(required=false)
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BootSecurityConfig bootSecurityConfig;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
    }

	@Override
    public void configure(WebSecurity web) throws Exception {
    }

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		AjaxAuthenticationHandler authHandler = new AjaxAuthenticationHandler();
		http
	    	.headers()
	    		.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsMode.SAMEORIGIN))
	    	.and()
	    	.rememberMe()
	    		.key(bootSecurityConfig.getRememberMe().getKey())
	    		.tokenValiditySeconds(bootSecurityConfig.getRememberMe().getTokenValiditySeconds());
		
		http.csrf().disable();
		
		http
			.authorizeRequests()
				.antMatchers("/user*").permitAll()
				.antMatchers("/security/**")
				.fullyAuthenticated();

		http.formLogin()
				.loginPage(WebConstants.BASE_PATH+"/login").permitAll()
	    		.loginProcessingUrl(WebConstants.BASE_PATH+"/dologin").permitAll()
				.successHandler(authHandler)
				.failureHandler(authHandler)
	    		.and()
	    	.logout()
	    		.deleteCookies("JSESSIONID")
	    		.invalidateHttpSession(true)
	    		.and()
	    	.sessionManagement()
	    		.maximumSessions(1);
//	    		.failureUrl("/login?loginError=1")
	    	;
    }
	
	

}
