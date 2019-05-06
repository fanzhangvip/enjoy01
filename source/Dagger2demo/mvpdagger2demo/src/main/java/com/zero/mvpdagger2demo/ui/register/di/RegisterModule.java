package com.zero.mvpdagger2demo.ui.register.di;

import com.zero.mvpdagger2demo.ui.register.RegisterContract;
import com.zero.mvpdagger2demo.ui.register.RegisterModel;
import com.zero.mvpdagger2demo.ui.register.RegisterPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * 模块
 * 可以理解为快递包裹，这是一个容器
 * 下面以@Provides修饰的方法就是这个包裹包含的物品
 * 在Component接口中通过@Component(modules =
 * xxxx.class),将容器需要的商品封装起来，统一交给快递员（Component），
 * 让快递员统一送到目标容器中
 * Module编写技巧：
 * 1. dagger使用的目的在于更多的使用面向接口的编程
 * 2. 构造具体的类的实现，但是返回接口
 */
@Module
public class RegisterModule {

    RegisterContract.View view;

    public RegisterModule(RegisterContract.View view) {
        this.view = view;
    }


    @Provides
    RegisterContract.View provideRegisterView() {
        return this.view;
    }


    @Provides
    RegisterContract.Model provideRegisterModel(RegisterModel registerModel) {
        return registerModel;
    }

    @Provides
    RegisterContract.Presenter providePresenter(RegisterPresenter presenter) {
        return presenter;
    }

}
