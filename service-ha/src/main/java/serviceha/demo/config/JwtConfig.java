package serviceha.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JwtConfig {

    public static final String public_cert = "public.cert";

    @Autowired
    private AdditionalJwtAccessTokenConverter additionalJwtAccessTokenConverter;

    @Bean
    @Qualifier("tokenStore")
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    protected JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        Resource resource =  new ClassPathResource(public_cert);
//
//        String publicKey;
//        try {
//            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
//        }catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        converter.setVerifierKey(publicKey);
        converter.setAccessTokenConverter(additionalJwtAccessTokenConverter);
        converter.setSigningKey("123");
        return converter;
    }
}
