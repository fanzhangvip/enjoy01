package com.zero.dagger2demo.a;

import dagger.Component;

/**
 * 用于标注接口，是依赖需求方和依赖提供方之间的桥梁。被Component标注的接口在编译时会生成该接口的实现类（如果@Component标注的接口为CarComponent，则编译期生成的实现类为DaggerCarComponent）,我们通过调用这个实现类的方法完成注入；
 */
@Component
public interface CarComponent {
    void inject(Car car);
}
