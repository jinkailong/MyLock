package com.nubesn.subway.android.mylock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class LockActivity extends AppCompatActivity {

    private View lockView;
    private LockUtil lockLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        Log.d("---------", "LockActivity");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        lockView = View.inflate(this, R.layout.lock_show, null);
        lockLayer = new LockUtil(this);
        lockView.findViewById(R.id.unlock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockLayer.unlock();
            }
        });
        lockLayer.setLockView(lockView);// 设置要展示的页面
        lockLayer.lock();// 开启锁屏
        finish();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return false;
//    }
}
