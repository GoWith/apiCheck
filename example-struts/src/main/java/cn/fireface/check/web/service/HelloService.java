package cn.fireface.check.web.service;

import cn.fireface.check.web.manager.HelloManager;
import cn.fireface.check.web.uitls.RandomTime;
import org.springframework.stereotype.Service;

/**
 * Created by fireface on 2018/10/29.
 * don't worry , be happy
 */
@Service
public class HelloService {
    public void sayHello() throws InterruptedException {
        new HelloManager().sayHello();
        new HelloManager().sayBye();
        Thread.sleep(RandomTime.next());
        System.out.println("hello service  say : hello");
    }
    public void sayBye() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new HelloManager().sayHello();
        }
        new HelloManager().sayHello();
        new HelloManager().sayBye();
        Thread.sleep(RandomTime.next());
        System.out.println("hello service  say : bye");
    }
}
