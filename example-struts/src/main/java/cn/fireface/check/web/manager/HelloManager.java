package cn.fireface.check.web.manager;

import cn.fireface.check.web.uitls.RandomTime;
import org.springframework.stereotype.Service;

/**
 * Created by maoyi on 2018/10/29.
 * don't worry , be happy
 */
@Service
public class HelloManager {
    public void sayHello() throws InterruptedException {
        Thread.sleep(RandomTime.next());
        System.out.println("hello manager say : hello");
    }
    public void sayBye() throws InterruptedException {
        Thread.sleep(RandomTime.next());
        System.out.println("hello manager say : bye");
    }
}
