package cn.fireface.check.web.action;

import cn.fireface.check.web.service.HelloService;
import cn.fireface.check.web.uitls.RandomTime;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by fireface on 2018/10/29.
 * don't worry , be happy
 */
public class HelloAction extends ActionSupport{
    public String hello() throws InterruptedException {
        Thread.sleep(RandomTime.next());
        new HelloService().sayHello();
        new HelloService().sayBye();
        return SUCCESS;
    }

    public String hi() throws InterruptedException {
        Thread.sleep(RandomTime.next());
        new HelloService().sayHello();
        new HelloService().sayBye();
        return SUCCESS;
    }
}
