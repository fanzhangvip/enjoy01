package com.enjoy.zero.libzhujie01.targetdemo;

import com.sun.tools.javac.main.Option;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.Consumer;

public class TargetTest {

    @Target(ElementType.TYPE_PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @interface TypeParameter{//泛型参数上使用
        String value() default "";
    }

    @Target(ElementType.TYPE_USE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface TypeUse{//除了class,其他地方都能用
        String value() default "";
    }

    static class Lambda<@TypeParameter E>{
        public <@TypeParameter("String") T> void getLambda(@TypeUse("name") T t){
            System.out.println("call getLambda");
        }
    }


    public static void main(String ... args){


        boolean bool = Lambda.class.isAnnotationPresent(TypeParameter.class);
        System.out.println("bool= " + bool);

        try {
            Lambda<String> lambda = new Lambda<>();
            AnnotatedType[] type = lambda.getClass().getAnnotatedInterfaces();
            Arrays.stream(type).forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
