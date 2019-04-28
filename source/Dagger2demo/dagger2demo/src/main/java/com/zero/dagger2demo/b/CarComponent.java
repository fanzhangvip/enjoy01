package com.zero.dagger2demo.b;

import dagger.Component;

@Component(modules = MarkCarModule.class)
public interface CarComponent {
    void inject(Car car);
}
