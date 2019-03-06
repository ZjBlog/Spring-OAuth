package oauth.demo.dto;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SmsCodeServiceImpl implements SmsCodeService {


    @Override
    public boolean check(String phoneNumber, String aCode) {
        if (!StringUtils.hasText(aCode)) {
            return false;
        }
        /**
         * 根据手机号获取code
         */
        String code = "123";
        if (aCode.equals(code)) {
            return true;
        }
        return false;
    }
}
