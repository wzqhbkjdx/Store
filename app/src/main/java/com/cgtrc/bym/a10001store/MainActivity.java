package com.cgtrc.bym.a10001store;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cgtrc.bym.a10001store.server.MyAsyncTask;
import com.cgtrc.bym.a10001store.ui.UpdateView;
import com.cgtrc.bym.a10001store.utils.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BYM on 2016/1/27.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private Button switch_text_voice_layout;
    private Button voice_start;
    private Button back_2_navigation_or_to_voice;
    private Button sendbtn_or_to_iask;
    private EditText text_start;
    private boolean voiceState = true;
    private RelativeLayout warning_layout;
    private ConnectivityManager cm;
    private ScrollView sv;
    private UpdateView updateView;
    private String question;
    private ProgressDialog dialog;
    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        checkNet();//在Activity建立的时候检查网络
        //网络状态检测
        /**
         * 1.后台线程一直运行（不推荐），每隔几秒钟检测一次当前的网络状态，如果网络不可用，则给出提示
         * 2.监听一个系统广播（推荐），android系统中网络状态发生改变的时候，系统会发送网络状态改变的广播
         */
        registerBroadcast();
        initClass();

//        String url = "http://192.168.1.100:8080/myserver.do";
//		new MyAsyncTask().execute(url);

    }

    private void initClass() {
        updateView = new UpdateView(this,sv);
        myAsyncTask = new MyAsyncTask(MainActivity.this,updateView);
    }

    class MyTestAsyncTask extends AsyncTask<String,Integer,Object>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.setTitle("提示");
            dialog.setMessage("玩命加载中，请稍后");
            dialog.setIcon(android.R.drawable.ic_menu_info_details);
            dialog.setCancelable(false);
            dialog.show();
        }


        @Override
        protected Object doInBackground(String... params) {
            String str = params[0];

            for(int i = 0; i < 10; i++){
                SystemClock.sleep(1000);
                publishProgress((i + 1) * 10);
            }
            return str + "加载成功";
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), (String) result , Toast.LENGTH_LONG).show();
            dialog.cancel();
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int position = values[0];
            dialog.setProgress(position);
        }
    }


    private void checkNet() {
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null && info.isAvailable() && info.isConnected()){
            //此时网络是可用的
            warning_layout.setVisibility(View.GONE);
        } else {
            //此时网络不可用
            warning_layout.setVisibility(View.VISIBLE);
        }
    }

    private void registerBroadcast() {
        //注册网络监听状态的广播
        IntentFilter netFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        netFilter.addCategory(Intent. CATEGORY_DEFAULT);
        registerReceiver(netReciver,netFilter);

        //注册activity销毁的广播
        IntentFilter destoryFilter = new IntentFilter(Constants.ACTIVITY_DESTORY_ACTION);
        destoryFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(destoryReciever,destoryFilter);

        //注册来自导航页的广播
        IntentFilter navigationFilter = new IntentFilter(Constants.NAVIGATION_STRING_ACTION);
        navigationFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(navigationReceiver,navigationFilter);

    }

    private BroadcastReceiver navigationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String key = Constants.NAVIGATION_STRING_ACTION_KEY;
            question = intent.getStringExtra(key);
            //去服务器找答案
            System.out.println(question);
            //更新场景2
            updateView.updateMyView(question);
            myAsyncTask.queryAnswer(question);


        }
    };

    /**
     * 相当于activity的destory方法
     */

    private BroadcastReceiver destoryReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterReceiver(netReciver);//销毁广播
            unregisterReceiver(navigationReceiver);
            unregisterReceiver(this);//自杀
        }
    };

    private BroadcastReceiver netReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                //获得系统的连接管理器,网络管理器
                NetworkInfo info = cm.getActiveNetworkInfo();//获得激活的网络信息
//                boolean available = info.isAvailable();//是否可用
//                boolean connected = info.isConnected();//是否已连接
//                String netType = info.getTypeName();//获取网络连接的类型
                if(info != null && info.isAvailable() && info.isConnected()){
                    //此时网络是可用的
                    warning_layout.setVisibility(View.GONE);
                } else {
                    //此时网络不可用
                    warning_layout.setVisibility(View.VISIBLE);
                }

            }
        }
    };

    /**
     * 不推荐该方法
     */
    @Deprecated
    private void checkNetOld(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                //检查网络是否可用，如果不可用，则通过handler发送主线程去显示提示
            }
        };
        timer.schedule(task,1000,3000);
    }



    private void initView() {
        switch_text_voice_layout = (Button) findViewById(R.id.switch_text_voice_layout);
        voice_start = (Button) findViewById(R.id.voice_start);
        back_2_navigation_or_to_voice = (Button) findViewById(R.id.back_2_navigation_or_to_voice);
        sendbtn_or_to_iask = (Button) findViewById(R.id.sendbtn_or_to_iask);
        text_start = (EditText) findViewById(R.id.text_start);
        warning_layout = (RelativeLayout) findViewById(R.id.warning_layout);
        sv = (ScrollView) findViewById(R.id.scolllayout);

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
                    //Toast.makeText(this,"viewpager滑动到爱问页",Toast.LENGTH_LONG).show();
                    //向pageActivity发送打开爱问页面的广播
                    Intent intent = new Intent(Constants.IASK_ACTION);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    sendBroadcast(intent);


                } else {
                    //Toast.makeText(this,"发送按钮",Toast.LENGTH_LONG).show();
                    //更新场景1
                    updateView.updateMyView(question);
                    myAsyncTask.queryAnswer(question);
                }
                break;
            default:
                break;
        }
    }
}
