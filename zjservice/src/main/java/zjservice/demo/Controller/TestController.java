package zjservice.demo.Controller;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zjservice.demo.service.EurekaClientFeign;

/**
 * @author : ZJ
 * @date : 19-4-17 下午1:55
 */
@RestController
public class TestController {


    /**
     * 自定义span数据
     */
    @Autowired
    Tracer tracer;

    @Autowired
    private EurekaClientFeign feign;

    @GetMapping("/test")
    public String test(){

        tracer.currentSpan().tag("name","forezp");
        return feign.sayHiFromClientEureka("123");
    }
}
