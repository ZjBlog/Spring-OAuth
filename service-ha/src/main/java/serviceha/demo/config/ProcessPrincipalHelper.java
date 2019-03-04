package serviceha.demo.config;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.util.StringUtils;
import serviceha.demo.entity.Role;
import serviceha.demo.entity.User;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author : ZJ
 * @date : 19-3-4 下午3:29
 */
public class ProcessPrincipalHelper {
    private static final String USERNAME = "user_name";

    private static  final String AUTHORITIES = AccessTokenConverter.AUTHORITIES;

    public static Object converter(Map<String, ?> map) {
        Map<String, Object> params = new HashMap<String, Object>();
        User processUser = new User();
        params= (Map<String, Object>) map.get(USERNAME);
        params.remove("authorities");

        List<GrantedAuthority> list=new ArrayList<>();
        for (GrantedAuthority grantedAuthority : list) {

        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
           list = AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            list = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        try {
            BeanUtils.populate(processUser,params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        List<Role> list1=new ArrayList<>();
        for (GrantedAuthority grantedAuthority : list) {
            list1.add(new Role(grantedAuthority.getAuthority()));
        }
        processUser.setAuthorities(list1);
        return processUser;
    }
}