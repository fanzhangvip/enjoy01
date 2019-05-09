package com.zero.dagger2demo.c;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MarkCarModule {

    public MarkCarModule() {
    }

    /**
     * 2. 同时我们需要对依赖提供方做出修改
     *
     * @return
     */
//    @Engine.QualifierA
    @Named("a")
    @Provides
    Engine provideEngineA() {
        return new Engine("gearA");
    }

    //    @Engine.QualifierB
    @Named("b")
    @Provides
    Engine provideEngineB() {
        return new Engine("gearB");
    }
}
