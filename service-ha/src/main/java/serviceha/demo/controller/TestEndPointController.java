package serviceha.demo.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import serviceha.demo.dto.UserService;
import serviceha.demo.entity.User;
import serviceha.demo.service.EurekaClientFeign;

import java.security.Principal;

@RestController
public class TestEndPointController {
    @Autowired
    private UserService userService;

    @Autowired
    private EurekaClientFeign feign;

    @Value("${test}")
    private String name;

    Logger logger = LoggerFactory.getLogger(TestEndPointController.class);
    @RequestMapping(value = "/registry", method = RequestMethod.POST)
    public User createUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password)) {
            return userService.create(username, password);
        }
        return null;
    }
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id : " + id;
    }
    @GetMapping("/getPrinciple")
    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication) {

        User user = (User) oAuth2Authentication.getPrincipal();

        return oAuth2Authentication;
    }

    @GetMapping("/getPrinciple1")
    public User getPrinciple(@AuthenticationPrincipal User user) {
        return user;
    }
    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "order id : " + id;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping("/hello")
    public String hello() {
        return "hello you";
    }

    @GetMapping("/test/1")
    public String hi(){
        return feign.sayHiFromClientEureka("9999") +"==="+name;
    }
}
