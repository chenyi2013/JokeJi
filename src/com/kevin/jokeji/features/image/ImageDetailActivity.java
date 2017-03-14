package com.kevin.jokeji.features.image;

import android.view.View;
import android.widget.RelativeLayout;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseActivity;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;

import java.util.HashMap;
import java.util.Map;

public class ImageDetailActivity extends BaseActivity implements IWXRenderListener {

    public final static String IMAGE_URL = "image_url";

    private WXSDKInstance mWXSDKInstance;
    private RelativeLayout mLayout;
    private static String TEST_URL = "http://192.168.101.105:12580/dist/main.js";


    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_detail;
    }

    @Override
    protected void initView() {

        /**
         * pageName:自定义，一个标示符号。
         * url:远程bundle JS的下载地址
         * options:初始化时传入WEEX的参数，比如 bundle JS地址
         * flag:渲染策略。WXRenderStrategy.APPEND_ASYNC:异步策略先返回外层View
         * ，其他View渲染完成后调用onRenderSuccess。WXRenderStrategy.APPEND_ONCE 所有控件渲染完后后一次性返回。
         */
        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, getIntent().getStringExtra(IMAGE_URL));
        mWXSDKInstance.renderByUrl("WXSample", TEST_URL, options, null, WXRenderStrategy.APPEND_ONCE);
        mLayout = (RelativeLayout) findViewById(R.id.activity_image_detail);
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        if (mLayout.getChildCount() > 0) {
            mLayout.removeAllViews();
        }
        mLayout.addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }


    @Override
    protected void initData() {

    }
}
