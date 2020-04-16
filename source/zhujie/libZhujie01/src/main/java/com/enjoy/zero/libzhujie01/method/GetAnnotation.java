package com.enjoy.zero.libzhujie01.method;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface MethodAnnotation {

    String key() default "default key";

    String value() default "default value";
}

public class GetAnnotation {
    @MethodAnnotation(key = "key11", value = "value22")
    public void test(String ... args) {}


    public static void main(String[] args) throws Exception {

        /**
         * getDefaultValue()
         *
         * 返会该注解方法对象表示的成员默认值
         *
         * 如果成员属于基本数据类型，则返回对应的包装类实例
         *
         * 如果没有默认值或者该方法实例不表示注解方法，则返回 null
         */
        Method key = MethodAnnotation.class.getMethod("key");
        Method value = MethodAnnotation.class.getMethod("value");
        Object defaultValue1 = key.getDefaultValue();
        Object defaultValue2 = value.getDefaultValue();
        // default key
        System.out.println(defaultValue1);
        // default value
        System.out.println(defaultValue2);


        Method method = GetAnnotation.class.getDeclaredMethod("test",String[].class);
        /**
         * isVarArgs()
         *
         * 如果该方法对象的参数中存在 可变参，则返回 true，否则为 false
         */
        // true
        System.out.println(method.isVarArgs());
        /**
         * getDeclaringClass()
         *
         * 返回该方法对象表示的方法所在类的 Class 对象
         */
        Class<?> declaringClass = method.getDeclaringClass();
        // class lang.reflect.MethodTest
        System.out.println("declaringClass: "+declaringClass);
        /**
         * isAnnotationPresent(Class<? extends Annotation> annotationClass)
         *
         * 如果该方法对象上有指定类型的注解，则返回 true，否则为 false
         */
        System.out.println(method.isAnnotationPresent(MethodAnnotation.class));
        /**
         * 如果该方法对象存在指定类型的注解，则返回该注解，否则返回 null
         *
         * 只有类级别的注解会被继承得到，对于其他对象而言，
         * getAnnotation() 方法与 getDeclaredAnnotation() 方法作用相同
         */
        MethodAnnotation annotation = method.getAnnotation(MethodAnnotation.class);
//        MethodAnnotation annotation = method.getDeclaredAnnotation(MethodAnnotation.class);
        // @com.enjoy.zero.libzhujie01.method.MethodAnnotation(key=key11, value=value22)
        System.out.println(annotation);
    }

}
