package cn.fireface.check.filter.strategy;

import cn.fireface.check.ParameterInfo;
import cn.fireface.check.beans.BeanMap;
import cn.fireface.check.filter.strategy.AbstractFilterStrategy;
import cn.fireface.check.filter.strategy.MyClassUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.text.Collator;
import java.util.*;

/**
 * Create by maoyi on 2018/12/28
 * don't worry be happy!
 *
 * @author maoyi
 * @date 2018-12-28 09:57
 */
public class ArgFilterStrategy extends AbstractFilterStrategy {
    @Override
    public String execute(Map<String, String[]> parameters) {
        String beanName = ((Object[]) parameters.get("beanName"))[0].toString();
        Object bean = BeanMap.getBean(beanName);
        Class<?> aClass =  bean.getClass();
        String methodName = ((Object[]) parameters.get("methodName"))[0].toString();
        Method realMethod = MyClassUtils.getRealMethod(aClass, methodName);

        ParameterInfo[] parameterInfos = MyClassUtils.getParameterInfos(realMethod,bean,methodName);

        List<Map<String, String>> args = new ArrayList<>();

        for (ParameterInfo info : parameterInfos) {
            Map<String, String> map = new HashMap<>();
            map.put("key", info.getClazz().getSimpleName() + " " + info.getName());
            map.put("value", JSON.toJSONString(mockFull(info)));
            args.add(map);
        }

        return returnJSONResult(RESULT_CODE_SUCCESS, args);
    }

    public static Object mockFull(ParameterInfo info) {
        Class clazz = info.getClazz();
        String name = clazz.getSimpleName();
        Object value = null;
        switch (name) {
            case "String":
                value = clazz.getName();
                break;
            case "Integer":
            case "int":
                value = 1;
                break;
            case "Long":
            case "long":
                value = 2018L;
                break;
            case "Short":
            case "short":
                value = (short)4;
                break;
            case "Double":
            case "double":
                value = (double)0.01;
                break;
            case "Date":
                value = new Date();
                break;
            case "List":
                Type[] types = getTypes(info.getType());
                List list = new ArrayList();
                for (int i = 0; i < 3; i++) {
                    list.add(mockFull(new ParameterInfo(null,(Class) types[0],types[0],null)));
                }
                value = list;
                break;
            case "Map":
                Type[] mapTypes = getTypes(info.getType());
                Map map = new HashMap();
                for (int i = 0; i < 3; i++) {
                    map.put(mockFull(new ParameterInfo(null,(Class) mapTypes[0],mapTypes[0],null)),mockFull(new ParameterInfo(null,(Class) mapTypes[1],mapTypes[1],null)));
                }
                value = map;
                break;
            default:
                try {
                    Object instance = clazz.newInstance();
                    Field[] declaredFields = clazz.getDeclaredFields();
                    for (Field declaredField : declaredFields) {
                        Class<?> type = declaredField.getType();
                        Object fieldValue = mockFull(new ParameterInfo(declaredField.getName(),declaredField.getType(),declaredField.getGenericType(),declaredField.getDeclaredAnnotations()));
                        int modifiers = declaredField.getModifiers();
                        String modifier = Modifier.toString(modifiers);
                        if(modifier.contains("final")){
                            continue;
                        }
                        declaredField.setAccessible(true);
                        declaredField.set(instance, fieldValue);
                    }
                    value = instance;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

        }
        return value;
    }
    private static Type[] getTypes(Type type){
        if ( type instanceof ParameterizedType) {
            return  ((ParameterizedType)type).getActualTypeArguments();
        }else {
            return null;
        }
    }
}
