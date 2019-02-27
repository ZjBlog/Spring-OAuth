package oauth.demo.config;

import oauth.demo.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : ZJ
 * @date : 19-2-26 下午4:11
 */
public class CustomerAccessTokenConverter extends DefaultAccessTokenConverter {
    public CustomerAccessTokenConverter() {
        super.setUserTokenConverter(new CustomerUserAuthenticationConverter());
    }


    private class CustomerUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

        @Override
        public Map<String, ?> convertUserAuthentication(Authentication authentication) {
            LinkedHashMap response = new LinkedHashMap();
            response.put("user_name", authentication.getName());
            response.put("name", ((User) authentication.getPrincipal()).getUsername());
            response.put("id", ((User) authentication.getPrincipal()).getId());
            response.put("createAt", ((User) authentication.getPrincipal()).getUsername());
            if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
                response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
            }

            return response;
        }
    }
}
