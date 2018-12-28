package cn.fireface.check.examples.pringboot.manager;

import org.springframework.stereotype.Service;

/**
 * Created by maoyi on 2018/10/29.
 * don't worry , be happy
 */
@Service
public class HelloManager {
    public void sayHello() throws InterruptedException {
        System.out.println("hello manager say : hello");
    }
    public void sayBye() throws InterruptedException {
        System.out.println("hello manager say : bye");
    }
}
