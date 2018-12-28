package cn.fireface.check.filter.strategy;

import cn.fireface.check.ParameterInfo;
import cn.fireface.check.beans.BeanMap;
import cn.fireface.check.filter.strategy.AbstractFilterStrategy;
import cn.fireface.check.filter.strategy.MyClassUtils;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.Collator;
import java.util.*;

/**
 * Create by maoyi on 2018/12/28
 * don't worry be happy!
 *
 * @author maoyi
 * @date 2018-12-28 09:57
 */
public class MethodFilterStrategy extends AbstractFilterStrategy {
    @Override
    public String execute(Map<String, String[]> parameters) {
        String beanName = ((Object[]) parameters.get("beanName"))[0].toString();
        Object bean = BeanMap.getBean(beanName);
        Method[] methods = bean.getClass().getDeclaredMethods();
        List<String> methodNames = new ArrayList<String>();
        for (Method method : methods) {
            StringBuilder methodName = new StringBuilder();
            String name = method.getName();
            methodName.append(name).append("(");
            Class<?>[] parameterTypes = method.getParameterTypes();
            ParameterInfo[] parameterInfos = MyClassUtils.getParameterInfos(method,bean,null);
            for (ParameterInfo info : parameterInfos) {
                methodName.append(info.getClazz().getSimpleName()).append(' ').append(info.getName()).append(",");
            }

            methodName.append(')');
            String methodNameStr = methodName.toString();
            if (methodNameStr.endsWith(",)")) {
                methodNameStr = methodNameStr.replace(",)", ")");
            }
            methodNames.add(methodNameStr);
        }
        Collections.sort(methodNames , Collator.getInstance(Locale.CHINA));
        return returnJSONResult(RESULT_CODE_SUCCESS, methodNames);
    }






}
