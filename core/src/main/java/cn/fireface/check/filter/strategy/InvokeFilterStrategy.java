package cn.fireface.check.filter.strategy;

import cn.fireface.check.ParameterInfo;
import cn.fireface.check.beans.BeanMap;
import cn.fireface.check.filter.strategy.AbstractFilterStrategy;
import cn.fireface.check.filter.strategy.MyClassUtils;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.*;
import java.util.*;

/**
 * Create by fireface on 2018/12/28
 * don't worry be happy!
 *
 * @author fireface
 * @date 2018-12-28 09:57
 */
public class InvokeFilterStrategy extends AbstractFilterStrategy {
    @Override
    public String execute(Map<String, String[]> parameters) {
        try {
            String beanName = ((Object[]) parameters.get("beanName"))[0].toString();
            Object bean = BeanMap.getBean(beanName);
            String methodName = ((Object[]) parameters.get("methodName"))[0].toString();

            Class<?> aClass =bean.getClass();
            Method realMethod = MyClassUtils.getRealMethod(aClass, methodName);
            ParameterInfo[] parameterInfos = MyClassUtils.getParameterInfos(realMethod,bean,methodName);
            Object[] args=new Object[parameterInfos.length];
            for (int i = 0; i < parameterInfos.length; i++) {
                String o = ((Object[]) parameters.get(parameterInfos[i].getClazz().getSimpleName() + " " + parameterInfos[i].getName()))[0] + "";
                args[i]=(JSON.parseObject(o, parameterInfos[i].getClazz()));
            }
            realMethod.setAccessible(true);
            Object invoke = realMethod.invoke(bean, args);
            return returnJSONResult(RESULT_CODE_SUCCESS, invoke);
        } catch (Exception e) {
            return returnJSONResult(RESULT_CODE_SUCCESS, e);
        }
    }
}
