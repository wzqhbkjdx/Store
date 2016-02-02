package com.cgtrc.bym.a10001store.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cgtrc.bym.a10001store.R;
import com.cgtrc.bym.a10001store.adapter.IAskAdapter;
import com.cgtrc.bym.a10001store.domain.IAsk;
import com.cgtrc.bym.a10001store.ui.XListView;
import com.cgtrc.bym.a10001store.utils.DataUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by BYM on 2016/1/29.
 */
public class IAskAnswerFragment extends Fragment implements XListView.IXListViewListener{
    private XListView listView;
    private View rootView;
    private SimpleDateFormat sdf;
    private List<IAsk> list;
    private IAskAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.iask_answer_fragment, null);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//该父类方法必须被调用
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);//该父类方法必须被调用
        listView = (XListView) rootView.findViewById(R.id.listview);
        listView.setPullLoadEnable(true);//开启加载更多
        listView.setPullRefreshEnable(true);//开启下拉刷新
        listView.setXListViewListener(this);//设置监听器
        sdf = DataUtil.getSimpleDateFormat();
        list = initData();
        adapter = new IAskAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }

    private List<IAsk> initData() {
        List<IAsk> dataList= new ArrayList<IAsk>();
        try {
            for (int i = 0; i < 15; i ++) {
                IAsk ask = new IAsk();
                ask.setTitle("今天准时吃饭！" + i );
                ask.setDetails("为什么中午吃红烧肉不好？" + i);
                ask.setAvatarId(R.drawable.user_icon);
                String date = "2016-01-31 15:54:" + i;
                ask.setAskTime(sdf.parse(date));
                ask.setScore(20 + i);
                ask.setAnswerCount(5 + i);
                dataList.add(ask);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    public void onRefresh() {
        //下来刷新的时候会出发该方法
        try{
            IAsk ask = new IAsk();
            ask.setTitle("下拉刷新加载的最新的标题");
            ask.setDetails("下拉刷新加载的问题的详情");
            ask.setAvatarId(R.drawable.user_icon);
            Random r = new Random();
            int s = r.nextInt(60);
            String date = "2016-01-31 19:00:" + s;
            ask.setAskTime(sdf.parse(date));
            ask.setScore(20);
            ask.setAnswerCount(5);
            list.add(ask);
            Collections.sort(list,comparator);
            //显示上次更新的时间
            listView.setRefreshTime(sdf.format(new Date()));

            adapter.notifyDataSetChanged();//通知adapter更新
            listView.stopRefresh();//让下拉刷新的效果消失


        }catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoadMore() {//网络通讯，用json解析后的数据封装成类再传给界面显示

        try{
            IAsk ask = new IAsk();
            ask.setTitle("上拉加载的历史数据");
            ask.setDetails("上拉加载的问题的详情");
            ask.setAvatarId(R.drawable.user_icon);
            Random r = new Random();
            int s = r.nextInt(60);
            String date = "2016-01-31 11:32:" + s;
            ask.setAskTime(sdf.parse(date));
            ask.setScore(20);
            ask.setAnswerCount(5);
            list.add(ask);
            adapter.notifyDataSetChanged();//通知adapter更新
            listView.stopLoadMore();//让上拉加载更多的效果消失

        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Comparator<IAsk> comparator = new Comparator<IAsk>() {
        @Override
        public int compare(IAsk i1, IAsk i2) {
            if(i1.getAskTime().getTime() < i2.getAskTime().getTime()){
                return 1;
            } else if(i1.getAskTime().getTime() > i2.getAskTime().getTime()){
                return -1;
            } else {
                return 0;
            }

        }
    };
}
