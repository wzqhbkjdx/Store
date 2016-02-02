package com.cgtrc.bym.a10001store;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cgtrc.bym.a10001store.adapter.ViewPagerAdapter;
import com.cgtrc.bym.a10001store.utils.BroadcastHelper;
import com.cgtrc.bym.a10001store.utils.Constants;
import com.cgtrc.bym.a10001store.utils.DensityUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BYM on 2016/1/27.
 */
public class PageActivity extends Activity implements View.OnClickListener{
    private LocalActivityManager lam;
    private ViewPager viewPager;
    public SlidingMenu sm;
    private Button moreBtn;
    private Animation topDownAnim;
    private Animation topUpAnim;
    private LinearLayout subMenu;
    private boolean animState = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_layout);
        initView();

        lam = new LocalActivityManager(this,true);
        lam.dispatchCreate(savedInstanceState);//该方法必须被调用 否则activity转化为view会报错
        initActivities();
        initSlidingMenu();
        topDownAnim = AnimationUtils.loadAnimation(this,R.anim.top_down);//加载动画资源
        topUpAnim = AnimationUtils.loadAnimation(this,R.anim.top_up);
        topDownAnim.setAnimationListener(topDownAnimListener);//设置动画执行后的监听处理
        topUpAnim.setAnimationListener(topUpAnimListener);

        registerBroadcast();


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //让viewPager第一页的时候滑出slidingMenu，第二页滑动回到第一页
                switch (position){
                    case 0:
                        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    default:
                        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //MainActivity的生命周期和PageAcitivity的生命周期绑定，所以PageActivity的生命周期结束的时候，要通知MainActivity
        //解除对BroadcastReceiver的注册
        BroadcastHelper.sendBroadCast(getApplicationContext(),Constants.ACTIVITY_DESTORY_ACTION,null,null);

        unregisterReceiver(smReceiver);
        unregisterReceiver(IAskRececiver);
        smReceiver = null;
        IAskRececiver = null;
    }

    private void registerBroadcast() {
        //接收来自MainActivity的广播 打开slidingMenu
        IntentFilter smIntentFilter = new IntentFilter(Constants.SLIDING_MENU_ACTION);
        smIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(smReceiver,smIntentFilter);

        IntentFilter IAskIntentFilter = new IntentFilter(Constants.IASK_ACTION);
        IAskIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(IAskRececiver,IAskIntentFilter);

    }

    private BroadcastReceiver smReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constants.SLIDING_MENU_ACTION)){
                sm.toggle();
            }
        }
    };

    private BroadcastReceiver IAskRececiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Constants.IASK_ACTION)){
                viewPager.setCurrentItem(1);
            }
        }
    };

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        moreBtn = (Button) findViewById(R.id.more_btn);
        moreBtn.setOnClickListener(this);
        subMenu = (LinearLayout) findViewById(R.id.sub_menu);

    }

    private void initSlidingMenu() {
        //实例化一个slidingmenu
        sm = new SlidingMenu(this);
        sm.setMode(SlidingMenu.LEFT);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        sm.setMenu(R.layout.navigation_layout);
        new NavigationHandler(this,sm);//实现导航页的动画 写在单独的类中
    }

    private void initActivities() {
        List<View> viewList = new ArrayList<View>();

        Intent intent = new Intent(this,MainActivity.class);
        //因为viewpager不能滑动activity所以需要将activity转换成view
        View mainview = lam.startActivity("MainActivity",intent).getDecorView();
        viewList.add(mainview);

        Intent intent2 = new Intent(this,IAskActivity.class);
        //将activity转换成view
        View iaskView = lam.startActivity("IAskActivity",intent2).getDecorView();
        viewList.add(iaskView);

        //将viewList放到ViewPager的适配器中去显示
        ViewPagerAdapter adapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_btn:
                //触发打开或者关闭动画
                if(animState){
                    //执行向下动画
                    animState = false;
                    subMenu.setVisibility(View.VISIBLE);
                    subMenu.startAnimation(topDownAnim);
                    //动画播放完毕固定位置
                }else {
                    //执行向上动画
                    animState = true;
                    subMenu.startAnimation(topUpAnim);
                }
                break;
            default:
                break;
        }
    }

    private Animation.AnimationListener topDownAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //让当前动画固定住，不要回到原来位置
            animation.setFillAfter(true);
            //为了响应点击事件，需要重新设定submenu的位置和宽高
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getApplicationContext(),70));//设置宽和高
            params.setMargins(0,DensityUtil.dip2px(getApplicationContext(),50),0,0);//设置位置
            subMenu.clearAnimation();//清掉submenu上的动画
            subMenu.setLayoutParams(params);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private Animation.AnimationListener topUpAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //让当前动画固定住，不要回到原来位置
            animation.setFillAfter(true);
            //为了响应点击事件，需要重新设定submenu的位置和宽高
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getApplicationContext(),70));//设置宽和高
            params.setMargins(0,0,0,0);//设置位置
            subMenu.clearAnimation();//清掉submenu上的动画
            subMenu.setLayoutParams(params);
            subMenu.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
