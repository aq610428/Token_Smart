package com.token.smart;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.webkit.WebView;


/**
 * Created 2016/10/8.
 */
public class BaseApplication extends Application {
    public static Context mContext;
    public static BaseApplication instance;
    private static final String PROCESSNAME = "com.token.smart";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        instance = this;
        initActivityLifecycle();
        updateBeta();
    }

    /************版本更新************/
    private void updateBeta() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        displayMetrics.scaledDensity = displayMetrics.density;
    }


    /********初始化Activity监听事件******/
    private void initActivityLifecycle() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
    }



    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName(this);
            if (!PROCESSNAME.equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }

    }

    public String getProcessName(Context context) {
        if (context == null) return null;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }

    public static Context getContext() {
        return mContext;
    }


    public static BaseApplication getInstance() {
        return instance;
    }


}