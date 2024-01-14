package org.sopt.lequuServer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableFeignClients
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@EnableAspectJAutoProxy
public class LequuServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LequuServerApplication.class, args);
    }

}
