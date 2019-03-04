package serviceha.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
//    @Autowired
//    private UserDao userRepository;
//
//    @Autowired
//    private OAuth2ClientProperties oAuth2ClientProperties;
//
//    @Autowired
//    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    @RequestMapping("/login")
//    public ResponseEntity<OAuth2AccessToken> login(@Valid UserLoginParamDto loginDto, BindingResult bindingResult) throws Exception {
//
//        if (bindingResult.hasErrors()){
//            throw new Exception("登录信息错误，请确认后再试");
//        }
//
//        User user = userRepository.findByUsername(loginDto.getUsername());
//
//        if (null == user){
//            throw new Exception("用户为空，出错了");}
//
//        if (!BPwdEncoderUtil.matches(loginDto.getPassword(), user.getPassword().replace("{bcrypt}","")))
//        {throw new Exception("密码不正确");}
//
//        String client_secret = oAuth2ClientProperties.getClientId()+":"+oAuth2ClientProperties.getClientSecret();
//
//        client_secret = "Basic "+Base64.getEncoder().encodeToString(client_secret.getBytes());
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Authorization",client_secret);
//
//        //授权请求信息
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.put("username", Collections.singletonList(loginDto.getUsername()));
//        map.put("password", Collections.singletonList(loginDto.getPassword()));
//        map.put("grant_type", Collections.singletonList(oAuth2ProtectedResourceDetails.getGrantType()));
//
//        map.put("scope", oAuth2ProtectedResourceDetails.getScope());
//        //HttpEntity
//        HttpEntity httpEntity = new HttpEntity(map,httpHeaders);
//        //获取 Token
//        return restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST,httpEntity,OAuth2AccessToken.class);
//
//    }
}
