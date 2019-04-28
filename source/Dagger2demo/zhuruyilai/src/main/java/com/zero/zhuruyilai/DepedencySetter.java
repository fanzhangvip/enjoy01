package com.zero.zhuruyilai;

/**
 * 接口方式注入
 * 接口的存在，表明了一种依赖配置的能力。
 */
public interface DepedencySetter {
    void set(Driveable driveable);
}
