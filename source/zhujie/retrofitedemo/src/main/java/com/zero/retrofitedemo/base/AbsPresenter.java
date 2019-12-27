package com.zero.retrofitedemo.base;

public abstract class AbsPresenter<M extends BaseModel, V extends BaseView> {
    private static final String TAG = AbsPresenter.class.getSimpleName();

    protected M mModel;
    protected V mView;

    //外部注入
    public abstract void setModel(M mModel);

    //外部注入
    public abstract void setView(V mView);
}
