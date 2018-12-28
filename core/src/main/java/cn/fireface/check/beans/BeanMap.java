package cn.fireface.check.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextScope;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create by fireface on 2018/12/27
 * don't worry be happy!
 *
 * @author fireface
 * @date 2018-12-27 15:34
 */
public class BeanMap {

    /**
     * 初始化上下文
     */
    private static WebApplicationContext appContext = ContextLoader.getCurrentWebApplicationContext();


    /**
     * 获取spring管理的bean列表
     *
     * @return 返回所有spring管理的bean列表
     */
    public static String[] getBeanNames() {
        if (appContext == null) {
            init();
        }
        return appContext.getBeanDefinitionNames();
    }

    /**
     * @param name bean 的name
     * @return 返回一个bean实例
     */
    public static Object getBean(String name) {
        return appContext.getBean(name);
    }

    private static void init() {
        appContext = ContextLoader.getCurrentWebApplicationContext();
    }

    public static void load(ServletContext servletContext){
        if(appContext == null) {
            appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        }
    }
}
