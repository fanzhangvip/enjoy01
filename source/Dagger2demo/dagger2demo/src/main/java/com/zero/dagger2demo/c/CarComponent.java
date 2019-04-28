package com.zero.dagger2demo.c;

import dagger.Component;

@Component(modules = MarkCarModule.class)
public interface CarComponent {
    void inject(Car car);
}
