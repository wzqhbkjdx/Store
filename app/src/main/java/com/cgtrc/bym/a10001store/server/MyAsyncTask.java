package com.cgtrc.bym.a10001store.server;

import android.content.Context;
import android.os.AsyncTask;

import com.cgtrc.bym.a10001store.R;
import com.cgtrc.bym.a10001store.ui.UpdateView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by BYM on 2016/2/2.
 */
public class MyAsyncTask {

    //用于模拟从网络上获取答案的过程
    //异步获取对象，将UpdateView的引用传递给该类，回调
    private Context context;
    private UpdateView updateView;
    public MyAsyncTask(Context context, UpdateView updateView){
        this.context = context;
        this.updateView = updateView;


    }

    /*
     *在服务端搜索请求答案
     */
    public void queryAnswer(String text){
        //异步请求答案
       new QueryAnswerAsyncTask().execute(text);

    }

    /**
     * 该类用于异步处理耗时任务 比new Thread + handler + message 简单 因为在主线程超过5秒五响应就ANR 且AnsyncTask效率更高，内部有线程池，但对资源的消耗比较大
     * Params:入口参数，如服务器的url地址
     * Progress:更新进度条的时候的进度单位类型 一般为int类型 Integer
     * Result:最终拿到结果的数据类型，如从服务器端解析的JSON数据封装为一个javaBean，则该参数就设置为javaBean类型
     *
     */
    class QueryAnswerAsyncTask extends AsyncTask<String,Void,Object>{

        /**
         * 该方法运行在主线程，一般用来给用户一个提示
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * 该方法运行在子线程中，用来进行耗时的处理，一般用来请求server或者需要长时间处理的任务
         * @param params
         * @return
         */
        @Override
        protected Object doInBackground(String... params) {
            String str = params[0];
            String result = getAnswer(str);

            return result;
        }

        /**
         *该方法运行在主线程用来取消在onPreExecute()方法中显示的对话框，一般在这个方法中更新UI
         * @param result 该参数类型与doInBackground的返回值类型以及Result的返回值类型必须保持一致
         */
        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            String content = (String )result;
            updateView.updateSheView(content);
        }
    }

    private String getAnswer(String str) {
        if(str.contains("笑话")){
            String[] jokeArray = context.getResources().getStringArray(R.array.joke);
            Random r = new Random();
            int indext = r.nextInt(jokeArray.length);
            String joke = jokeArray[indext];
            return joke;
        }else if(str.contains("美女")){
            int resId = randomMM();
            return resId + "";
        }
        return null;
    }

    private int randomMM() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(R.drawable.mm0);
        list.add(R.drawable.mm1);
        list.add(R.drawable.mm2);
        list.add(R.drawable.mm3);
        list.add(R.drawable.mm4);
        list.add(R.drawable.mm5);
        list.add(R.drawable.mm6);
        list.add(R.drawable.mm7);
        list.add(R.drawable.mm8);
        list.add(R.drawable.mm9);
        list.add(R.drawable.mm10);
        list.add(R.drawable.mm11);
        list.add(R.drawable.mm12);
        list.add(R.drawable.mm13);
        list.add(R.drawable.mm14);
        list.add(R.drawable.mm15);
        list.add(R.drawable.mm16);
        list.add(R.drawable.mm17);
        list.add(R.drawable.mm18);
        list.add(R.drawable.mm19);
        list.add(R.drawable.mm20);
        list.add(R.drawable.mm21);
        list.add(R.drawable.mm22);
        list.add(R.drawable.mm23);

        Random r = new Random();
        int index = r.nextInt(list.size());
        int resId = list.get(index);

        return resId;
    }

}
