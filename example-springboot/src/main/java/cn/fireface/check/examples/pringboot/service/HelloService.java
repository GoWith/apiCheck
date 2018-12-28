package cn.fireface.check.examples.pringboot.service;

import cn.fireface.check.examples.pringboot.manager.HelloManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fireface on 2018/10/29.
 * don't worry , be happy
 */
@Service
public class HelloService {

    /**
     *
     */
//    private final HelloManager helloManager;

//    /**
//     * @param helloManager
//     */
//    @Autowired
//    public HelloService(HelloManager helloManager) {
//        this.helloManager = helloManager;
//    }

    public void sayHello() throws InterruptedException {
//        helloManager.sayHello();
//        helloManager.sayBye();
        System.out.println("hello service  say : hello");
    }
    public void sayBye() throws InterruptedException {
//        helloManager.sayHello();
//        helloManager.sayBye();
        System.out.println("hello service  say : bye");
    }
}
