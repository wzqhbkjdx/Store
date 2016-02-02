package com.cgtrc.bym.a10001store.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cgtrc.bym.a10001store.R;
import com.cgtrc.bym.a10001store.utils.DataUtil;

/**
 * Created by BYM on 2016/2/2.
 */
public class UpdateView {

    private ScrollView scrollView;
    private LinearLayout baseLayout;
    private Context context;

    public UpdateView(Context context, ScrollView scrollView) {
        this.context = context;
        this.scrollView = scrollView;
        this.baseLayout = (LinearLayout) scrollView.findViewById(R.id.base_layout);

    }

    public void updateMyView(String text){
        //更新我说的话在MainActivity上显示
        View myView = InflateView.inflateMyView(context);
        TextView date = (TextView) myView.findViewById(R.id.messagedetail_row_date);
        TextView content = (TextView) myView.findViewById(R.id.messagedetail_row_text);

        date.setText(DataUtil.getCurrentTime());
        content.setText(text);
        baseLayout.addView(myView);//将我的场景更新到scrollView里的baselayout上
        scrollToBottom();
    }

    public void updateSheView(String text){
        View sheView = InflateView.inflateSheView(context);
        TextView date = (TextView) sheView.findViewById(R.id.messagedetail_row_date);
        TextView content = (TextView) sheView.findViewById(R.id.messagedetail_row_text);

        date.setText(DataUtil.getCurrentTime());
        content.setText(text);
        baseLayout.addView(sheView);
        scrollToBottom();
    }

    /**
     * 有新对话产生的时候，scrollView自动滚动到底部，让对话显示出来
     */
    private void scrollToBottom() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
