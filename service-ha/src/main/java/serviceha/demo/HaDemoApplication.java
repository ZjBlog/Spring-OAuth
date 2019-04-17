package serviceha.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


//@EnableOAuth2Client
@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class HaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaDemoApplication.class, args);
    }

}
