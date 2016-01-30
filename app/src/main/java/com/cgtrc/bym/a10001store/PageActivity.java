package com.cgtrc.bym.a10001store;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.cgtrc.bym.a10001store.adapter.ViewPagerAdapter;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BYM on 2016/1/27.
 */
public class PageActivity extends Activity {
    private LocalActivityManager lam;
    private ViewPager viewPager;
    SlidingMenu sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);

        lam = new LocalActivityManager(this,true);
        lam.dispatchCreate(savedInstanceState);//该方法必须被调用 否则activity转化为view会报错
        initActivities();
        initSlidingMenu();
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
}
