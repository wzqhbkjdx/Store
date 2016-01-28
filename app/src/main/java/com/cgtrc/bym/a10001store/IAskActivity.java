package com.cgtrc.bym.a10001store;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by BYM on 2016/1/27.
 */
public class IAskActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setText(IAskActivity.class.getSimpleName());
    }
}
