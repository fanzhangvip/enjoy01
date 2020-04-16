package com.enjoy.zero.libzhujie01.method;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;


@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@interface ParameterAnnotation {

    String key();

    String value();
}
public class GetAnnotatedParameterTypes {


    public void test(@ParameterAnnotation(key = "key1", value = "value1") String name,
                     @ParameterAnnotation(key = "key2", value = "value2") Integer age) {}


    public static void main(String[] args) throws Exception {
        Method method = GetAnnotatedParameterTypes.class.getDeclaredMethod("test", String.class, Integer.class);

        /**
         * getParameters()
         *
         * 返回一个参数对象数组，该数组表示该方法对象的所有参数
         */
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            // java.lang.String name
            // java.lang.Integer age
            System.out.println(parameter);
        }

        /**
         * getParameterAnnotations()
         *
         * 返回一组注解数组，这些注解以声明顺序修饰该方法对象的参数
         */
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        // [[@lang.reflect.ParameterAnnotation(key=key1, value=value1)], [@lang.reflect.ParameterAnnotation(key=key2, value=value2)]]
        System.out.println(Arrays.deepToString(parameterAnnotations));

        /**
         * getParameterCount()
         *
         * 返回该方法对象的参数个数 (无论是显式声明的还是隐式声明的或不声明的)
         */
        System.out.println(method.getParameterCount());
        /**
         * getAnnotatedParameterTypes()
         *
         * 返回一个 AnnotatedType 对象数组，这些对象表示使用类型来指定由该可执行文件表示的方法或构造函数的形式参数类型
         */
        AnnotatedType[] annotatedParameterTypes = method.getAnnotatedParameterTypes();
        for (AnnotatedType annotatedParameterType : annotatedParameterTypes) {
            // class java.lang.String
            // class java.lang.Integer
            System.out.println(annotatedParameterType.getType());
        }
    }

}
