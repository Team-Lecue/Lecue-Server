package org.sopt.lequuServer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
@OpenAPIDefinition(servers = {@Server(url ="https://www.lecue.p-e.kr", description = "Default Server URL")})
@EnableFeignClients
public class LequuServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LequuServerApplication.class, args);
	}

}
