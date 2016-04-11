package com.nubesn.subway.android.mylock;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * 锁屏工具类
 * @author tongleer.com
 *
 */
public class LockUtil {
    private Context mContext;
    private WindowManager mWindowManager;// 窗口管理器
    private View mLockView;// 锁屏视图
    private LayoutParams wmParams;
    private static LockUtil mLockUtil;
    private boolean isLocked;// 是否锁定

    public static synchronized LockUtil getInstance(Context mContext) {
        if (mLockUtil == null) {
            mLockUtil = new LockUtil(mContext);
        }
        return mLockUtil;
    }
    /**
     * 构造方法
     * @param mContext
     */
    public LockUtil(Context mContext) {
        this.mContext = mContext;
        init();
    }
    /**
     * 初始化布局
     */
    private void init() {
        isLocked = false;
// 获取WindowManager
        mWindowManager = (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
// 设置LayoutParams(全局变量）相关参数
        wmParams = new LayoutParams();
        wmParams.type = LayoutParams.TYPE_SYSTEM_ERROR;// 关键部分
        wmParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
// 设置Window flag
        wmParams.width = LayoutParams.FILL_PARENT;
        wmParams.height = LayoutParams.FILL_PARENT;
        wmParams.flags = 1280;
    }
    /**
     * 锁屏
     */
    public synchronized void lock() {
        Log.d("----------","lock");
        if (mLockView != null && !isLocked) {
            Log.d("----------","addView");
            mWindowManager.addView(mLockView, wmParams);
        }
        isLocked = true;
    }
    /**
     * 解锁
     */
    public synchronized void unlock() {
        if (mWindowManager != null && isLocked) {
            mWindowManager.removeView(mLockView);
        }
        isLocked = false;
    }
    /**
     * 更新
     */
    public synchronized void update() {
        if (mLockView != null && !isLocked) {
            mWindowManager.updateViewLayout(mLockView, wmParams);
        }
    }
    /**
     * 设置锁屏视图
     * @param v
     */
    public synchronized void setLockView(View v) {
        mLockView = v;
    }
}
