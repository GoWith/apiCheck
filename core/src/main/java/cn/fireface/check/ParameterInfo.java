package cn.fireface.check;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 *
 * @author fireface
 * @date 2018/5/8
 * don't worry , be happy
 */
public class ParameterInfo {
    private String name;
    private Class clazz;
    private Type type;
    private Annotation[] annotations;

    public ParameterInfo(String name , Class clazz , Type type ,Annotation[] annotations){
        this.name = name;
        this.clazz = clazz;
        this.type = type;
        this.annotations = annotations;
    }

    public String getName() {
        return name;
    }

    public Class getClazz() {
        return clazz;
    }

    public Type getType() {
        return type;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }
}
