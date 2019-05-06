package com.zero.mvpdagger2demo01.ui.register;



import com.zero.mvpdagger2demo01.base.BaseModel;
import com.zero.mvpdagger2demo01.base.BasePresenter;
import com.zero.mvpdagger2demo01.base.BaseView;

import io.reactivex.Observable;

public interface RegisterContract {

    /**
     * 对于Presenter层他是连接View层与Model层的桥梁并对业务逻辑进行处理。
     * 在MVP架构中Model与View无法直接进行交互。所以在Presenter层它会从Model层获得所需要的数据，
     * 进行一些适当的处理后交由View层进行显示。这样通过Presenter将View与Model进行隔离，
     * 使得View和Model之间不存在耦合，同时也将业务逻辑从View中抽离
     */
    interface Presenter extends BasePresenter {
        /**
         * 注册
         * @param mobile
         * @param password
         * @param rePassword
         */
        void register(String mobile, String password, String rePassword);
    }

    /**
     * 对于View层也是视图层，在View层中只负责对数据的展示，
     * 提供友好的界面与用户进行交互。
     * 在Android开发中通常将Activity或者Fragment作为View层
     */
    interface View extends BaseView<Presenter> {
        /**
         * 注册成功
         *
         * @param result
         */
        void registerSuccess(Model result);
    }

    /**
     * 对于Model层也是数据层。它区别于MVC架构中的Model，在这里不仅仅只是数据模型。
     * 在MVP架构中Model它负责对数据的存取操作，例如对数据库的读写，网络的数据的请求等
     */
    interface Model extends BaseModel {
        String getMsg();

        String getUser();

        boolean isSucess();

        /**
         * 注册
         *  @param mobile
         * @param password
         * @param rePassword
         */
        Observable<Model> register(String mobile, String password, String rePassword);
    }
}
