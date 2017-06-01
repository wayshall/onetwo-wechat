package org.onetwo4j.sample;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
@EnableConfigurationProperties(WechatConfig.class)
public class WechatStarter {
	
	@RequestMapping("/")
	public String home() {
        return "index";
    }

    public static void main(String[] args) {
//        SpringApplication.run(WechatStarter.class, args);
        new SpringApplicationBuilder(WechatStarter.class)
        							.web(true)
        							.build()
        							.run(args);
    }
}
