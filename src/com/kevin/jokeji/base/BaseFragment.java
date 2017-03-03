package com.kevin.jokeji.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kevin.jokeji.R;
import com.kevin.jokeji.view.TitleBar;


/**
 * Created by kevin on 17/1/4.
 */

public abstract class BaseFragment extends Fragment {


    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private ProgressDialog mProgressDialog;

    private BaseActivity mActivity;

    protected abstract void initView();

    protected abstract void initData();
    protected abstract void loadData();

    protected <T> T findViewById(int id) {
        return (T) mContentView.findViewById(id);
    }

    protected void setListener() {
    }

    protected void onRetry() {
    }

    protected TitleBar getTitleBar() {
        return mActivity.getTitleBar();
    }

    protected void setTitle(String title) {
        mActivity.setTitle(title);
    }

    protected void setTitle(int resId) {
        mActivity.setTitle(resId);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity = (BaseActivity) getActivity();
    }

    protected abstract int getLayoutId();

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_base, container, false);
        View view = inflater.inflate(getLayoutId(), relativeLayout, false);

        relativeLayout.addView(view);
        mErrorView = relativeLayout.findViewById(R.id.ll_retry);
        mLoadingView = relativeLayout.findViewById(R.id.loading_view);
        mContentView = view;
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetry();
            }
        });
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);

        return relativeLayout;
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        setListener();
        loadData();
    }


    private void setVisible(View view) {
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }


    private void setInVisible(View view) {
        if (view != null && view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }


    /**
     * 显示loading视图
     */
    public void showLoading() {
        setVisible(mLoadingView);
        setInVisible(mErrorView);
        setInVisible(mContentView);
    }

    /**
     * 显示错误视图
     */
    public void showErrorInfo() {
        setVisible(mErrorView);
        setInVisible(mLoadingView);
        setInVisible(mContentView);
    }


    /**
     * 显示内容视图
     */
    public void showContent() {
        setVisible(mContentView);
        setInVisible(mLoadingView);
        setInVisible(mErrorView);
    }

    /**
     * 如果show为true则显示进度条对话框，否则关闭对话框.
     *
     * @param message 对话所要显示的提示消息
     */
    protected void showProgressDialog(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        mProgressDialog.dismiss();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
