package com.zero.dagger2demo.d;

import dagger.Component;

/**
 * 3. 同时还需要使用@Scope去标注注入器Compoent
 */
@Engine.CarScope
@Component(modules = MarkCarModule.class)
public interface CarComponent {
    void inject(Car car);
}
