

package com.zero.mvpdemo.base;

public interface BaseView<T> {

    /**
     * 把p层和view 层关联起来
     * @param presenter
     */
    void setPresenter(T presenter);

}
