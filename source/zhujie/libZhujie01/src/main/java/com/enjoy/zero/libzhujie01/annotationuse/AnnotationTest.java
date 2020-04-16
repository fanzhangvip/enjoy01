package com.enjoy.zero.libzhujie01.annotationuse;


import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationTest {

    @Retention(RetentionPolicy.RUNTIME)
    @interface ClassAnnotation{
        int id() default -1;
        String className() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface ValueAnnotation{
        int value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface MethodAnnotation{

    }

    @ClassAnnotation(id = 1,className = "Test1")
    static class Test1{

    }

    @ClassAnnotation(id = 2,className = "Test2")
    static class Test2{
        @ValueAnnotation(2)
        int myInt;

        @MethodAnnotation
        @ValueAnnotation(10)
        public void testMethod(){}

        @SuppressWarnings("deprecation")
        public void testMethod1(){

        }
    }

    public static void main(String ... args){
        test1();
        System.out.println("======================");
        test2();
    }

    public static void test1(){

        boolean hasAnnotation = Test1.class.isAnnotationPresent(ClassAnnotation.class);
        System.out.println("hasAnnotation= " + hasAnnotation);
        if(hasAnnotation){
            ClassAnnotation classAnnotation = Test1.class.getAnnotation(ClassAnnotation.class);

            System.out.println("id= " + classAnnotation.id() + " ,className= " + classAnnotation.className());
        }

    }

    public static void test2(){
        boolean hasAnnotation = Test2.class.isAnnotationPresent(ClassAnnotation.class);
        System.out.println("hasAnnotation= " + hasAnnotation);
        if(hasAnnotation){
            ClassAnnotation classAnnotation = Test2.class.getAnnotation(ClassAnnotation.class);
            System.out.println("id= " + classAnnotation.id() + " ,className= " + classAnnotation.className());
        }
        try{
            Field  myIntField = Test2.class.getDeclaredField("myInt");
            myIntField.setAccessible(true);
            //获取一个成员变量上的注解
            ValueAnnotation valueAnnotation = myIntField.getAnnotation(ValueAnnotation.class);
            if(valueAnnotation !=null){
                System.out.println("valueAnnotation= " + valueAnnotation.value());
            }

            //1. 拿到method对象
            Method testMethod = Test2.class.getDeclaredMethod("testMethod");
            if(testMethod!=null){
                // 获取方法中的注解
                Annotation[] ans = testMethod.getAnnotations();
                    for(int i = 0; i < ans.length; i++){
                        System.out.println("method testMethod ans= " +ans[i].annotationType().getSimpleName());
                    }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
