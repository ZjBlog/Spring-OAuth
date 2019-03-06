package oauth.demo.dto;

/**
 * 短信验证码服务类
 */
public interface SmsCodeService {

    boolean check(String phoneNumber, String code);

}
