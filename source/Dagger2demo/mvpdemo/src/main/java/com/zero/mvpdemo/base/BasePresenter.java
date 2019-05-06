

package com.zero.mvpdemo.base;

public interface BasePresenter {

    /**
     * 初始化数据
     */
    void start();

    /**
     * 销毁 尤其是销毁view
     * 当view的生命周期结束后，Presenter把应该继续持有view的引用，从而引起泄露
     */
    void onDestory();

}
