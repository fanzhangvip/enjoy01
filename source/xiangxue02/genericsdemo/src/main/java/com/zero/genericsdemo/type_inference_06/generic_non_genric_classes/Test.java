package com.zero.genericsdemo.type_inference_06.generic_non_genric_classes;

import java.util.Collections;
import java.util.List;

//Target Types（目标类型）
public class Test {

    public static void main(String[] args) {

//        Collections.emptyList();
        //该语句需要List<String>的实例；此数据类型是目标类型。
        // 因为方法emptyList返回类型为List<T>的值，所以编译器推断类型参数T必须为值String
        List<String> listOne = Collections.emptyList();

        processStringList(listOne);

        processStringList(Collections.<String>emptyList());

        //jdk 7
        //List<Object> cannot be converted to List<String>
        processStringList(Collections.emptyList());//报错，java8可以
        //jdk 8 ok
        //目标类型的概念已经扩展到包括方法参数，例如方法processStringList的参数。
        // 在这种情况下，processStringList需要一个List<String>类型的参数。
        // 方法Collections.emptyList返回一个List<T>的值，
        // 因此，使用List<String>的目标类型，编译器会推断出类型参数T的值为String
       //jdk8的目标类型
      //  Java compiler can determine a target type:

//        Variable declarations
//        Assignments
//        Return statements
//        Array initializers
//        Method or constructor arguments
//        Lambda expression bodies
//        Conditional expressions, ?:
//        Cast expressions

        processStringList(Test.<String>emptyList());

        processStringList(Test.emptyList());//报错，java8可以

    }
    static <T> List<T> emptyList(){
        return null;
    }
    static void processStringList(List<String> stringList){

    }
}
