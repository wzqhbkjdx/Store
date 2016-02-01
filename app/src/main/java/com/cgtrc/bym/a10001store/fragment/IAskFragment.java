package com.cgtrc.bym.a10001store.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cgtrc.bym.a10001store.R;

/**
 * Created by BYM on 2016/1/29.
 */
public class IAskFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);//该父类方法必须被调用
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_layout,null);
        TextView tv = (TextView) view.findViewById(R.id.tv2);
        tv.setText(IAskFragment.class.getSimpleName());
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//该父类方法必须被调用
    }
}
