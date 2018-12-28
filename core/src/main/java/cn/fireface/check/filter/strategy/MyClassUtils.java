package cn.fireface.check.filter.strategy;

import cn.fireface.check.ParameterInfo;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by maoyi on 2018/12/28
 * don't worry be happy!
 *
 * @author maoyi
 */
public class MyClassUtils {
    /**
     *
     * @param clazz 类名
     * @param methodName 方法名
     * @return
     */
    public static Method getRealMethod(Class clazz, String methodName) {
        Method realMethod = null;
        if (methodName.endsWith("()")) {
            try {
                realMethod = clazz.getMethod(methodName.substring(0, methodName.indexOf('(')));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if (null == realMethod) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            List<Method> methods = new ArrayList<>();
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.getName().equalsIgnoreCase(methodName.substring(0, methodName.indexOf('(')))) {
                    methods.add(declaredMethod);
                }
            }

            for (Method method : methods) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                String[] split = methodName.split(",");
                if (parameterTypes.length != split.length) {
                    continue;
                }
                boolean findRightOne = false;
                for (Class<?> parameterType : parameterTypes) {
                    if (!methodName.contains(parameterType.getSimpleName())) {
                        findRightOne = false;
                        break;
                    }
                    findRightOne = true;
                }
                if (findRightOne) {
                    realMethod = method;
                    break;
                }
            }

        }
        return realMethod;
    }

    public static ParameterInfo[] getParameterInfos(Method method, Object bean, String methodName){
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Type[] types = method.getGenericParameterTypes();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameterNames = (new LocalVariableTableParameterNameDiscoverer()).getParameterNames(method);
        if(null == parameterNames && parameterTypes != null){
            try {
                if (bean != null){
                    Object target = getTarget(bean);
                    Method realMethod = methodName == null ? target.getClass().getDeclaredMethod(method.getName(),method.getParameterTypes()) : MyClassUtils.getRealMethod(target.getClass(), methodName);
                    Type[] _parameterTypes = realMethod.getGenericParameterTypes();
                    String[] _parameterNames = (new LocalVariableTableParameterNameDiscoverer()).getParameterNames(realMethod);
                    parameterNames = _parameterNames;
                    types = _parameterTypes;
                }
                if (parameterNames == null) {
                    throw new RuntimeException("can't get parameters");
                }
            } catch (Exception e) {
                parameterNames = new String[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameterNames[i] = "arg" + i;
                }
            }

        }
        ParameterInfo[] parameterInfos = new ParameterInfo[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameterInfos[i] = new ParameterInfo(parameterNames[i],parameterTypes[i],types[i],parameterAnnotations[i]);
        }
        return parameterInfos;
    }

    public static Object getTarget(Object proxy) throws Exception{

        if(!AopUtils.isAopProxy(proxy)){
            return proxy;
        }

        if(AopUtils.isJdkDynamicProxy(proxy)){
            return getJdkDynamicProxyTargetObject(proxy);
        }
        else{
            return getCglibProxyTargetObject(proxy);
        }

    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception{

        Field h=proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy=(AopProxy) h.get(proxy);
        Field advised=aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target=((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();

        return target;

    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception{

        Field h=proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor=h.get(proxy);
        Field advised=dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        Object target=((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();

        return target;

    }
}
