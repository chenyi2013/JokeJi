package com.kevin.jokeji.base;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.kevin.jokeji.R;
import com.kevin.jokeji.view.TitleBar;


public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 上下文对象
     */

    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private TitleBar mTitleBar;
    private ProgressDialog mProgressDialog;


    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initData();
        initTitleBar();
        initView();
        setListener();
        loadData();
    }

    protected abstract void loadData();

    public boolean isImmersive() {
        return true;
    }


    protected abstract int getLayoutId();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(getLayoutInflater().inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(R.layout.activity_base);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_base_mall);

        if (params != null) {
            if (params instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) params).addRule(RelativeLayout.BELOW, R.id.title_bar);
                relativeLayout.addView(view, params);
            } else {
                throw new RuntimeException("the layout params must is relayout params");
            }
        } else {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.title_bar);
            relativeLayout.addView(view, layoutParams);

        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);

        mErrorView = findViewById(R.id.ll_retry);
        mLoadingView = findViewById(R.id.loading_view);
        mContentView = view;
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRetry();
            }
        });
    }

    protected abstract void initView();

    protected abstract void initData();


    private void initTitleBar() {
        boolean isImmersive = false;
        if (hasKitKat() && !hasLollipop() && isImmersive()) {
            isImmersive = true;
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
        } else if (hasLollipop() && isImmersive()) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#ffe000"));
            isImmersive = true;
        }

        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setImmersive(isImmersive);
        mTitleBar.setBackgroundColor(Color.parseColor("#ffe000"));
        mTitleBar.setLeftTextColor(Color.WHITE);
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleBar.setTitleColor(Color.BLACK);
        mTitleBar.setSubTitleColor(Color.WHITE);
        mTitleBar.setDividerColor(Color.parseColor("#e5e5e5"));
        mTitleBar.setActionTextColor(Color.WHITE);
        mTitleBar.setTitle(getString(R.string.app_name));
    }


    public TitleBar getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitleBar.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        mTitleBar.setTitle(titleId);
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
     * 点击页面重试
     */
    protected void onRetry() {

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


    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    protected void setListener() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
