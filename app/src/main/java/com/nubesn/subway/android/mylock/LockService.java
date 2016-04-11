package com.nubesn.subway.android.mylock;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class LockService extends Service {
    private ScreenOffReceiver screenOffReceiver;
    private ScreenOnReceiver screenOnReceiver;
    public LockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        screenOnReceiver=new ScreenOnReceiver();
        IntentFilter screenOnFilter=new IntentFilter();
        screenOnFilter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(screenOnReceiver, screenOnFilter);
        screenOffReceiver=new ScreenOffReceiver();
        IntentFilter screenOffFilter=new IntentFilter();
        screenOffFilter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(screenOffReceiver, screenOffFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    /**
     * 监听屏幕变亮的广播接收器，变亮就屏蔽系统锁屏
     * @author tongleer.com
     *
     */
    private class ScreenOnReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals("android.intent.action.SCREEN_ON")){
                Log.d("--------","SCREEN_ON");
                KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
                KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock("");
                lock.disableKeyguard();
                Intent i1=new Intent(context,LockActivity.class);
                i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i1);
/*
* 此方式已经过时，在activtiy中编写
* getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
* getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
* 两句可以代替此方式
*/
/*KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
KeyguardLock lock = keyguardManager.newKeyguardLock("");
lock.disableKeyguard();*/
            }
        }
    }
    /**
     * 监听屏幕变暗的广播接收器，变暗就启动应用锁屏界面activity
     * @author tongleer.com
     *
     */
    private class ScreenOffReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals("android.intent.action.SCREEN_OFF")){
                Log.d("--------","SCREEN_OFF");
//                Intent i1=new Intent(context,LockActivity.class);
//                i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i1);
            }
        }
    }
}
