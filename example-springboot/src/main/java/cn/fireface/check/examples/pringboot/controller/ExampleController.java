package cn.fireface.check.examples.pringboot.controller;

import cn.fireface.check.examples.pringboot.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fireface on 2018/10/29.
 * don't worry , be happy
 */
@Controller
public class ExampleController {
    
    private final HelloService helloService;

    /**
     * @param helloService
     */
    @Autowired
    public ExampleController(HelloService helloService) {
        this.helloService = helloService;
    }

    @RequestMapping("/")
    public String index() throws InterruptedException {
        helloService.sayHello();
        helloService.sayBye();
        return "index";
    }
}
