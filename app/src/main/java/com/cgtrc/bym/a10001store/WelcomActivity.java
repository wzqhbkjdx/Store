package com.cgtrc.bym.a10001store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class WelcomActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        //欢迎页面
        //拿到当前Activity的layout的view
        //绑定透明度变化的动画
        //动画执行完毕进入主画面 加入一个新版本检查

        View view = findViewById(R.id.welcome_layout);

        AlphaAnimation animation = new AlphaAnimation(0.1f,1.0f);
        animation.setDuration(3000);
        view.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画执行完毕后，将当前动画跳转到PageActivity
                Intent intent = new Intent(WelcomActivity.this,PageActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //监听back键按下的时候什么也不处理
        //直接在当前页面忽略任何按键
//        if(keyCode ==  KeyEvent.KEYCODE_BACK){
//            //监听返回键
//        }
        //直接返回false可以在该Activity下使得点击任何按键都不起作用
        return false;
    }
}
