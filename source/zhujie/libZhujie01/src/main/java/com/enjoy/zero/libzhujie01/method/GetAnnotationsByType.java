package com.enjoy.zero.libzhujie01.method;


import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RepeatableAnnotation.class)
@interface MethodAnnotation1 {

    String key();

    String value();
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface RepeatableAnnotation {
    MethodAnnotation1[] value();
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface TestAnnotation {

    String key();

    String value();
}

public class GetAnnotationsByType {

    @MethodAnnotation1(key = "key1", value = "value1")
    @MethodAnnotation1(key = "key2", value = "value2")
    @TestAnnotation(key = "key2", value = "value2")
    public void test() {}

    public static void main(String[] args) throws Exception {
        Method method = GetAnnotationsByType.class.getDeclaredMethod("test");
        // null
        System.out.println(method.getAnnotation(MethodAnnotation1.class));
        /**
         * getAnnotationsByType(Class<T> annotationClass)
         *
         * 如果该方法对象存在指定类型的注解，则返回该注解数组，否则返回 null
         *
         * 只有类级别的注解会被继承得到，对于其他对象而言，getAnnotationsByType() 方法与 getDeclaredAnnotationsByType() 方法作用相同
         *
         * getAnnotationsByType() 方法与 getAnnotation() 方法的区别在于：getAnnotationsByType() 方法会检查修饰该方法对象的注解是否为可重复类型注解，如果是则会返回修饰该方法对象的一个或多个注解
         *
         * @Repeatable 用于声明注解为可重复类型注解
         *
         * 当声明为可重复类型注解后，如果方法注解仍为一个，则 getAnnotation() 方法会正常返回，如果方法注解为多个，则 getAnnotation() 方法会返回 null
         * getDeclaredAnnotationsByType(Class<T> annotationClass)
         *
         * 如果该方法对象存在指定类型的注解，则返回该注解数组，否则返回 null
         *
         * 只有类级别的注解会被继承得到，对于其他对象而言，getAnnotationsByType() 方法与 getDeclaredAnnotationsByType() 方法作用相同
         */
        MethodAnnotation1[] annotationsByType = method.getAnnotationsByType(MethodAnnotation1.class);
        // [@lang.reflect.MethodAnnotation(value=value1, key=key1), @lang.reflect.MethodAnnotation(value=value2, key=key2)]
        System.out.println(Arrays.toString(annotationsByType));

        /**
         * getAnnotations()
         *
         * 返回该方法对象上的所有注解，如果没有注解，则返回空数组
         *
         * 只有类级别的注解会被继承得到，对于其他对象而言，getAnnotations() 方法与 getDeclaredAnnotations() 方法作用相同
         */
        System.out.println("==================");
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            // @lang.reflect.MethodAnnotation(value=value1, key=key1)
            // @lang.reflect.Parameter.TestAnnotation(key=key2, value=value2)
            System.out.println(annotation);
        }
    }

}
