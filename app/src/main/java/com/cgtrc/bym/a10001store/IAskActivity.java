package com.cgtrc.bym.a10001store;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.cgtrc.bym.a10001store.adapter.IAskFragmentAdapter;

import org.w3c.dom.Text;

/**
 * Created by BYM on 2016/1/27.
 */
public class IAskActivity extends FragmentActivity implements View.OnClickListener{
    private ViewPager pager;
    private TextView iask_ask_text;
    private TextView iask_answer_text;
    private TextView iask_person_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iask_layout_manager_fragment);

        initView();

        //准备适配器，滑动3个fragment
        final IAskFragmentAdapter iAskFragmentAdapter = new IAskFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(iAskFragmentAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setIAskTextState();
                        break;
                    case 1:
                        setIAskAnswerState();
                        break;
                    case 2:
                        setIAskPersonState();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.iask_main_view_pager);
        iask_ask_text = (TextView) findViewById(R.id.iask_ask_text);
        iask_answer_text = (TextView) findViewById(R.id.iask_answer_text);
        iask_person_text = (TextView) findViewById(R.id.iask_person_text);

        iask_ask_text.setOnClickListener(this);
        iask_answer_text.setOnClickListener(this);
        iask_person_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iask_ask_text:
                pager.setCurrentItem(0);
                //设置按钮的按下状态
                setIAskTextState();
                break;
            case R.id.iask_answer_text:
                pager.setCurrentItem(1);
                //设置按钮的按下状态
                setIAskAnswerState();
                break;
            case R.id.iask_person_text:
                pager.setCurrentItem(2);
                //设置按钮的按下状态
                setIAskPersonState();
                break;
        }
    }

    private void setIAskPersonState() {
        iask_ask_text.setBackgroundResource(R.drawable.iask_bottom_tab_person_bg);
        iask_answer_text.setBackgroundResource(R.drawable.iask_bottom_tab_person_bg);
        iask_person_text.setBackgroundResource(R.drawable.iask_bottom_tab_ask_pressed);
    }

    private void setIAskAnswerState() {
        iask_ask_text.setBackgroundResource(R.drawable.iask_bottom_tab_person_bg);
        iask_answer_text.setBackgroundResource(R.drawable.iask_bottom_tab_ask_pressed);
        iask_person_text.setBackgroundResource(R.drawable.iask_bottom_tab_person_bg);
    }

    private void setIAskTextState() {
        iask_ask_text.setBackgroundResource(R.drawable.iask_bottom_tab_ask_pressed);
        iask_answer_text.setBackgroundResource(R.drawable.iask_bottom_tab_answer_bg);
        iask_person_text.setBackgroundResource(R.drawable.iask_bottom_tab_person_bg);
    }
}
