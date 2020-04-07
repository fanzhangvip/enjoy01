package com.enjoy.zero.libzhujie01.method;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.LinkedList;


public class GetParameterTypes<T> {
    interface Interface {

        default void testdefault() {
            System.out.println("这是一个默认方法");
        }
    }

    static class MethodTest<T, V> implements Interface{

        public <T, V> void test() {}

        public static void main(String[] args) throws Exception {

            Method method0 = MethodTest.class.getMethod("testdefault");
            /**
             * isDefault()
             *
             * 判断该方法对象是否为默认方法，如果是则返回 true，否则为 false
             */
            // true
            System.out.println(method0.isDefault());
            Method method = MethodTest.class.getMethod("test");
            /**
             * getTypeParameters()
             *
             * 返回一个 TypeVariable 对象数组，该数组表示该方法对象声明列表上的类型变量数组
             */
            TypeVariable<Method>[] typeParameters = method.getTypeParameters();
            // [T, V]
            System.out.println(Arrays.toString(typeParameters));

            /**
             * toString()
             *
             * 返回该方法对象的字符串表示形式 (擦除泛型)
             */
            // public void lang.reflect.MethodTest.test()
            System.out.println(method.toString());

            /**
             * toGenericString()
             *
             * 返回该方法对象的字符串表示形式 (保留泛型)
             */
            // public <T,V> void lang.reflect.MethodTest.test()
            System.out.println(method.toGenericString());
        }

    }

    public void test(T t, LinkedList<Integer> list) {}

    public T test1(T t) {
        return t;
    }

    public <T extends Exception> void test2() throws T, NullPointerException {}

    public static void main(String[] args) throws Exception {
        Method method = GetParameterTypes.class.getMethod("test", Object.class, LinkedList.class);
        /**
         * getParameterTypes()
         *
         * 返回一个 Class 对象数组，该数组以声明顺序表示该方法对象的参数对象 (擦除泛型)
         */
        Class<?>[] parameterTypes = method.getParameterTypes();
        // [class java.lang.Object, class java.util.LinkedList]
        System.out.println(Arrays.toString(parameterTypes));

        /**
         *getReturnType()
         *
         * 返回一个 Class 对象，该 Class 对象表示该方法对象的返回对象 (擦除泛型)
         */
        Method method1 = GetParameterTypes.class.getMethod("test1", Object.class);
        Class<?> returnType = method1.getReturnType();
        // class java.lang.Object
        System.out.println(returnType);
        /**
         * getGenericReturnType()
         *
         * 返回一个 Type 对象，该 Type 对象表示该方法对象的返回类型 (保留泛型)
         */
        Type genericReturnType = method1.getGenericReturnType();
        // T
        System.out.println("genericReturnType: "+genericReturnType);

        /**
         *getExceptionTypes()
         *
         * 返回一个 Class 对象数组，该数组表示由该方法对象抛出的异常对象 (擦除泛型)
         */
        Method method2 = GetParameterTypes.class.getMethod("test2");
        Class<?>[] exceptionTypes = method2.getExceptionTypes();
        // [class java.lang.Exception, class java.lang.NullPointerException]
        System.out.println(Arrays.toString(exceptionTypes));

        /**
         * getGenericExceptionTypes()
         *
         * 返回一个 Type 对象数组，该数组表示由该方法对象抛出的异常类型 (保留泛型)
         */
        Method method3 = GetParameterTypes.class.getMethod("test2");
        Type[] genericExceptionTypes = method3.getGenericExceptionTypes();
        // [T, class java.lang.NullPointerException]
        System.out.println(Arrays.toString(genericExceptionTypes));


    }

}
