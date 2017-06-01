package org.onetwo4j.sample.controller;

import javax.validation.Valid;

import org.onetwo.boot.core.web.controller.AbstractBaseController;
import org.onetwo4j.sample.request.ServerAuthRequest;
import org.onetwo4j.sample.service.ServerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wayshall
 * <br/>
 */
@RestController
@RequestMapping("server")
public class ServerController extends AbstractBaseController {
	
	@Autowired
	private ServerServiceImpl serverService;
	
	@GetMapping("auth")
	public String auth(@Valid ServerAuthRequest authRequet){
		return serverService.auth(authRequet);
	}

}
