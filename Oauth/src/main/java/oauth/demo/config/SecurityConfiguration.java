package oauth.demo.config;

import oauth.demo.config.Mobile.CustomAuthenticationProvider;
import oauth.demo.dto.SmsCodeService;
import oauth.demo.dto.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SmsCodeService smsCodeService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/oauth/**","/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable();

//        http.csrf().disable().requestMatchers().anyRequest()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/oauth/**").permitAll();
//                 http.formLogin().permitAll()
//                // 登出页
//                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
//                // 其余所有请求全部需要鉴权认证
//               .and().authorizeRequests().anyRequest().authenticated()
//                // 由于使用的是JWT，我们这里不需要csrf
//                .and().csrf().disable();


    }
    // 注入自定义的用户获取
    @Bean
    UserDetailsService customUserService() {
        return new UserServiceDetail();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(customAuthenticationProvider());
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置userDetailsService
        provider.setUserDetailsService(customUserService());
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        // 使用BCrypt进行密码的hash
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){
        CustomAuthenticationProvider customAuthenticationProvider=new CustomAuthenticationProvider();
        customAuthenticationProvider.setUserDetailsService(customUserService());
        customAuthenticationProvider.setSmsCodeService(smsCodeService);
        return customAuthenticationProvider;
    };
}
