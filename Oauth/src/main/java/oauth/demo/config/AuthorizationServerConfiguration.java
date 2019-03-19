package oauth.demo.config;

import oauth.demo.config.Mobile.CustomTokenGranter;
import oauth.demo.dto.UserServiceDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    @Qualifier("authenticationManagerBean")
    AuthenticationManager authenticationManager;


    @Autowired
    private UserServiceDetail userServiceDetail;

    static final Logger logger = LoggerFactory.getLogger(AuthorizationServerConfiguration.class);


    @Autowired
    private  CustomerAccessTokenConverter customerAccessTokenConverter;
    /**
     * 对称
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        converter.setAccessTokenConverter(customerAccessTokenConverter);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");

        logger.info("finalSecret === " + finalSecret);
        // 配置两个客户端，一个用于password认证一个用于client认证
        clients.inMemory().withClient("client_1")
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("select")
                .authorities("oauth2")
                .secret(finalSecret)
                .and().withClient("client_2")
                .authorizedGrantTypes("password", "refresh_token","mobile","authorization_code","implicit")
                .scopes("server").accessTokenValiditySeconds(3600).refreshTokenValiditySeconds(3600*30)
                .authorities("oauth2").redirectUris("https://www.bangechengzi.com/")
                .secret(finalSecret);
       // clients.withClientDetails(clientDetailsService);
    }

    /**
     * 自定义token生成方式
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         *         TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
         *         tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter()));
         *         endpoints.tokenStore(tokenStore())
         *                 .tokenEnhancer(tokenEnhancerChain).tokenServices(defaultTokenServices())
         *                 .authenticationManager(authenticationManager).tokenGranter(tokenGranter(endpoints));
         */
        endpoints.tokenServices(defaultTokenServices()).
                authenticationManager(authenticationManager)
                .tokenGranter(tokenGranter(endpoints)).userDetailsService(userServiceDetail).allowedTokenEndpointRequestMethods(HttpMethod.POST,HttpMethod.GET);
        endpoints.pathMapping("/oauth/confirm_access","/custom/confirm_access");
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单认证TokenEndpointTokenEndpoint
        security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(tokenStore());
        services.setTokenEnhancer(jwtAccessTokenConverter());
        //可以支持之歌
        services.setSupportRefreshToken(true);
        return services;
    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer config) {

        ClientDetailsService clientDetailsService = config.getClientDetailsService();
        AuthorizationServerTokenServices tokenServices = config.getTokenServices();
        OAuth2RequestFactory requestFactory = config.getOAuth2RequestFactory();

        List<TokenGranter> granters = new ArrayList<TokenGranter>(
                Arrays.asList(config.getTokenGranter()));

        granters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager,
                tokenServices,
                clientDetailsService, requestFactory));

        granters.add(new RefreshTokenGranter(tokenServices,
                clientDetailsService,
                requestFactory));

        granters.add(new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory));

        granters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));

        granters.add(new CustomTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));

        return new CompositeTokenGranter(granters);
    }
}
