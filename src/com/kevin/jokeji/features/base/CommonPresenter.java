package com.kevin.jokeji.features.base;

/**
 * Created by kevin on 17/3/2.
 */

public class CommonPresenter<T> implements BasePresenter<T> {

    private BaseModel<T> baseModel;
    private BaseView<T> baseView;

    public CommonPresenter(BaseModel<T> baseModel, BaseView<T> baseView) {
        this.baseModel = baseModel;
        this.baseView = baseView;
        baseModel.setPresenter(this);
    }


    @Override
    public void loadData(String url) {
        baseModel.loadData(url);
    }

    @Override
    public void onGetData(T t) {
        baseView.showData(t);
    }

    @Override
    public void onError(Exception exception) {
        baseView.showError();
    }
}
