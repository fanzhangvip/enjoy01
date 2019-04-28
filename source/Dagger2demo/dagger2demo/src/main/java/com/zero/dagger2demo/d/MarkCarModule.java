package com.zero.dagger2demo.d;


import dagger.Module;
import dagger.Provides;

@Module
public class MarkCarModule {

    public MarkCarModule(){ }

    /**
     * 2. @CarScope去标记依赖提供方MarkCarModule
     * @return
     */
    @Engine.CarScope
    @Provides
    Engine provideEngine(){
        return new Engine("gear");
    }

}
