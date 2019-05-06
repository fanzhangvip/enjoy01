package com.zero.mvpdagger2demo.base;

import javax.inject.Inject;

public abstract class AbsPresenter<M extends BaseModel, V extends BaseView> {
    private static final String TAG = AbsPresenter.class.getSimpleName();

//    @Inject
    protected M mModel;

//    @Inject
    protected V mView;

}
