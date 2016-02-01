package com.cgtrc.bym.a10001store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtrc.bym.a10001store.utils.Constants;

/**
 * Created by BYM on 2016/1/27.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private Button switch_text_voice_layout;
    private Button voice_start;
    private Button back_2_navigation_or_to_voice;
    private Button sendbtn_or_to_iask;
    private EditText text_start;
    private boolean voiceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();

    }

    private void initView() {
        switch_text_voice_layout = (Button) findViewById(R.id.switch_text_voice_layout);
        voice_start = (Button) findViewById(R.id.voice_start);
        back_2_navigation_or_to_voice = (Button) findViewById(R.id.back_2_navigation_or_to_voice);
        sendbtn_or_to_iask = (Button) findViewById(R.id.sendbtn_or_to_iask);
        text_start = (EditText) findViewById(R.id.text_start);

        switch_text_voice_layout.setOnClickListener(this);
        back_2_navigation_or_to_voice.setOnClickListener(this);
        sendbtn_or_to_iask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_text_voice_layout:
                //输入框的语音模式变为文本模式
                //1.将语音输入的按钮隐藏
                //2.将back_2_navigation_or_to_voice显示的图标更换
                //3.将自己隐藏
                //4.将sendbtn_or_to_iask按钮显示的图标更换
                //5.将EditText显示出来
                voice_start.setVisibility(View.GONE);
                back_2_navigation_or_to_voice.setBackgroundResource(R.drawable.voice_btn_bg);
                switch_text_voice_layout.setVisibility(View.GONE);
                sendbtn_or_to_iask.setBackgroundResource(R.drawable.send_btn_bg2);
                text_start.setVisibility(View.VISIBLE);
                voiceState = false;
                break;
            case R.id.back_2_navigation_or_to_voice:
                //从语音到文本模式的转换
                /**
                 * 1.将back_2_navigation_or_to_voice显示的图标恢复为默认
                 * 2.将EditText隐藏
                 * 3.将voice_start显示
                 * 4.将sendbtn_or_to_iask显示的图标更换
                 * 5.将switch_text_voice_layout显示
                 */
                if(voiceState){
                    //打开slidingmenu
                    //向pageActivity发送打开slidingmenu的广播
                    Intent intent = new Intent(Constants.SLIDING_MENU_ACTION);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    sendBroadcast(intent);

                }else{
                    back_2_navigation_or_to_voice.setBackgroundResource(R.drawable.introduction_btn_bg);
                    text_start.setVisibility(View.GONE);
                    voice_start.setVisibility(View.VISIBLE);
                    sendbtn_or_to_iask.setBackgroundResource(R.drawable.iask_bottom_btn_bg);
                    switch_text_voice_layout.setVisibility(View.VISIBLE);
                    voiceState = true;
                }

                break;
            case R.id.sendbtn_or_to_iask:
                if(voiceState){
                    Toast.makeText(this,"viewpager滑动到爱问页",Toast.LENGTH_LONG).show();
                    //向pageActivity发送打开爱问页面的广播
                } else {
                    Toast.makeText(this,"发送按钮",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
}
