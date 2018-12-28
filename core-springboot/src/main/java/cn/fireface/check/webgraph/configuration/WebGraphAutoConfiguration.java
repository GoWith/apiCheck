package cn.fireface.check.webgraph.configuration;

import cn.fireface.check.beans.BeanMap;
import cn.fireface.check.filter.WebFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Created by maoyi on 2018/10/29.
 * don't worry , be happy
 */
@Configuration
public class WebGraphAutoConfiguration {

    /**
     * 注册一个Druid内置的StatViewServlet，用于展示Druid的统计信息。
     */
    @Bean
    FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebFilter());
        filterRegistrationBean.addUrlPatterns("/check/*");
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);
        filterRegistrationBean.setDispatcherTypes(dispatcherTypes);
        return filterRegistrationBean;
    }
}
