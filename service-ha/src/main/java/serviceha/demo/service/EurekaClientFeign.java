package serviceha.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : ZJ
 * @date : 19-4-16 下午8:58
 */
@FeignClient(value = "service-sleuth")
public interface EurekaClientFeign {
    @GetMapping(value = "/hi")
    String sayHiFromClientEureka(@RequestParam(value = "name") String name);
}
