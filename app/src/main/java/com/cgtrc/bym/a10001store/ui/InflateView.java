package com.cgtrc.bym.a10001store.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.cgtrc.bym.a10001store.R;

/**
 * Created by BYM on 2016/2/2.
 */
public class InflateView {

    public static View inflateMyView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_say_me_item,null);
        return view;
    }
    public static View inflateSheView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_say_she_item,null);
        return view;
    }
}
