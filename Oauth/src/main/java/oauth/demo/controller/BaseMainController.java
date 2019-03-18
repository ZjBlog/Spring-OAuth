package oauth.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yuit
 * @date  2018/10/9  15:09
 *
 **/
@Controller
public class BaseMainController {



    @GetMapping("/auth/login")
    public String loginPage(Model model){

        model.addAttribute("loginProcessUrl","/oauth/authorize");

        return "login";
    }

}
