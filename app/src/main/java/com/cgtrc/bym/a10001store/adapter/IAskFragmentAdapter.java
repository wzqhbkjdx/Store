package com.cgtrc.bym.a10001store.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.cgtrc.bym.a10001store.fragment.IAskAnswerFragment;
import com.cgtrc.bym.a10001store.fragment.IAskFragment;
import com.cgtrc.bym.a10001store.fragment.IAskPersonFragment;


/**
 * Created by BYM on 2016/1/29.
 */
public class IAskFragmentAdapter extends FragmentPagerAdapter {


    public IAskFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    //向外返回当前要显示的fragment
    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        if(position == 0){
            f = new IAskFragment();
        }else if(position == 1){
            f = new IAskAnswerFragment();
        }else if(position == 2){
            f = new IAskPersonFragment();
        }
        return f;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
