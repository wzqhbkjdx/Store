package com.cgtrc.bym.a10001store.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by BYM on 2016/1/27.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> list;
    public ViewPagerAdapter(List<View> list){
        this.list = list;
    }

    /**
     * 一共有多少项
     * @return
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 判断当前view是不是来自object
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * item被销毁的时候
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        ViewPager vp = (ViewPager) container;
        View view = list.get(position);
        vp.removeView(view);
    }

    /**
     * item被生成的时候
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewPager vp = (ViewPager) container;
        View view = list.get(position);
        vp.addView(view);
        return view;

    }
}
