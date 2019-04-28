package com.zero.dagger2demo.c;


import dagger.Module;
import dagger.Provides;

@Module
public class MarkCarModule {

    public MarkCarModule(){ }

    /**
     * 2. 同时我们需要对依赖提供方做出修改
     * @return
     */
    @Engine.QualifierA
    @Provides
    Engine provideEngineA(){
        return new Engine("gearA");
    }

    @Engine.QualifierB
    @Provides
    Engine provideEngineB(){
        return new Engine("gearB");
    }
}
