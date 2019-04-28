package com.zero.dagger2demo.b;


import dagger.Module;
import dagger.Provides;

/**
 * 用于标注提供依赖的类。你可能会有点困惑，上面不是提到用@Inject标记构造函数就可以提供依赖了么，为什么还需要@Module？很多时候我们需要提供依赖的构造函数是第三方库的，我们没法给它加上@Inject注解，又比如说提供以来的构造函数是带参数的，如果我们之所简单的使用@Inject标记它，那么他的参数又怎么来呢？@Module正是帮我们解决这些问题的
 */
@Module
public class MarkCarModule {

    public MarkCarModule(){ }

    /**
     * 用于标注Module所标注的类中的方法，该方法在需要提供依赖时被调用，从而把预先提供好的对象当做依赖给标注了@Inject的变量赋值
     * @return
     */
    @Provides
    Engine provideEngine(){
        return new Engine("gear");
    }
}
