package cloud.sleuth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ZJ
 * @date : 19-4-16 下午8:57
 */
@RestController
public class ControlelrInfo {

    @Value("${test}")
    private String name1;

    @GetMapping("/hi")
    public String home(@RequestParam String name) {
        System.out.println(name);
        return "hi "+name+",i am from port,"+name1;
    }

}
