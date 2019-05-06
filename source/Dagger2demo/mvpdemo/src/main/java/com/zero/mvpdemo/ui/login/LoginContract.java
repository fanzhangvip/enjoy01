package com.zero.mvpdemo.ui.login;

import com.zero.mvpdemo.base.BasePresenter;
import com.zero.mvpdemo.base.BaseView;

/**
 * 方便管理接口
 */
public interface LoginContract {
    /**
     * 这里没有Model层，Presenter层充当了Model层的职责
     */
    interface Presenter extends BasePresenter{
        /**
         * 登录
         * @param mobile
         * @param password
         */
        void login(String mobile, String password);
    }

    interface View extends BaseView<Presenter>{
        /**
         * 登录成功
         *
         * @param result
         */
        void loginSuccess(String result);
    }
}
