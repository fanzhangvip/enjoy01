package com.zero.genericsdemo02.demo04;

import java.util.List;

public class Test {


}

class WildcardFixed {

    void foo(List<?> i) {
//        i.set(0,i.get(0));
        fooHelper(i);
    }

    /**
     * 在此示例中，代码正在尝试执行安全操作，那么如何解决编译器错误？
     * 你可以通过编写捕获通配符的私有帮助器方法来修复它。在这种情况下，
     * 你可以通过创建私有帮助器方法fooHelper来解决此问题
     *
     * 由于使用了辅助方法，编译器在调用中使用推断来确定T是CAP＃1（捕获变量）。该示例现在可以成功编译。

     * 按照约定，辅助方法通常命名为originalMethodNameHelper
     */

    private <T> void fooHelper(List<T> l) {
        l.set(0, l.get(0));
    }

}