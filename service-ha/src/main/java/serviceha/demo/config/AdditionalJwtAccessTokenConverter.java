package serviceha.demo.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : ZJ
 * @date : 19-3-4 下午3:50
 */
@Component
public class AdditionalJwtAccessTokenConverter extends JwtAccessTokenConverter {

    private DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();


    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        /**
         * AdditionalUserAuthenticationConverter 注入token转化器
         */
        UserAuthenticationConverter userAuthenticationConverter =
                new AdditionalUserAuthenticationConverter();
        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        return accessTokenConverter.extractAuthentication(map);
    }

}