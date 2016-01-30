package com.cgtrc.bym.a10001store;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgtrc.bym.a10001store.anim.ExpandAnimation;
import com.cgtrc.bym.a10001store.domain.ViewBean;
import com.cgtrc.bym.a10001store.utils.BroadcastHelper;
import com.cgtrc.bym.a10001store.utils.Constants;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 前提：将所有的title和对应的layout封装到ViewBean中，然后将所有ViewBean装入到List中
 * 1.点击任意一个title的时候，将该title对应的隐藏的layout显示出来，并将该title置为选中状态
 * 2.在点击其他title时，需要判断之前有没有打开过动画
 * 3.如果打开过，需要将之前打开的动画关闭，并且将之前按下的效果恢复成默认
 * Created by BYM on 2016/1/28.
 */
public class NavigationHandler {
    private static final String TAG = "NavigationHandler";
    private Activity activity;
    private List<ViewBean> viewList;
    private boolean animState = true;
    private View lastView;
    private SlidingMenu sm;

    public NavigationHandler(Activity activity,SlidingMenu sm) {
        this.activity = activity;
        this.sm = sm;
        initViews();
    }

    private void initViews() {
        viewList = new ArrayList<ViewBean>();
        //1.将所有的viewTitle和viewLayout封装成ViewBean，并且用list保存
        ViewBean bean = new ViewBean();
        View view = activity.findViewById(R.id.navigation_root_view);
        //初始化营业厅
        RelativeLayout businessHallTitle = (RelativeLayout) view.findViewById(R.id.find_business_hall_title);
        final View businessHallLayout = view.findViewById(R.id.business_hall_layout);
        bean.setViewTitle(businessHallTitle);
        bean.setViewLayout(businessHallLayout);
        viewList.add(bean);

        //初始化国际漫游
        RelativeLayout internationalRoamingTitle = (RelativeLayout) view.findViewById(R.id.international_roaming_title);
        final View internationalRoamingLayout = view.findViewById(R.id.international_roaming_layout);
        bean = new ViewBean();
        bean.setViewTitle(internationalRoamingTitle);
        bean.setViewLayout(internationalRoamingLayout);
        viewList.add(bean);

        //初始化查找手机
        RelativeLayout cellPhoneTitle = (RelativeLayout) view.findViewById(R.id.find_cellphone_title);
        final View cellPhoneLayout = view.findViewById(R.id.find_cellphone_layout);
        bean = new ViewBean();
        bean.setViewTitle(cellPhoneTitle);
        bean.setViewLayout(cellPhoneLayout);
        viewList.add(bean);

        //初始化套餐
        RelativeLayout packageTitle = (RelativeLayout) view.findViewById(R.id.package_title);
        final View packageLayout = view.findViewById(R.id.package_layout);
        bean = new ViewBean();
        bean.setViewTitle(packageTitle);
        bean.setViewLayout(packageLayout);
        viewList.add(bean);

        //初始化实时话费
        RelativeLayout telephoneChargeTitle = (RelativeLayout) view.findViewById(R.id.telephone_charge_title);
        final View telephoneChargeLayout = view.findViewById(R.id.telephone_charge_layout);
        bean = new ViewBean();
        bean.setViewTitle(telephoneChargeTitle);
        bean.setViewLayout(telephoneChargeLayout);
        viewList.add(bean);

        //初始化拨打客服电话
        RelativeLayout customerServicePhoneTitle = (RelativeLayout) view.findViewById(R.id.call_customer_service_phone_title);
        final View customerServicePhoneLayout = view.findViewById(R.id.call_customer_service_phone_layout);
        bean = new ViewBean();
        bean.setViewTitle(customerServicePhoneTitle);
        bean.setViewLayout(customerServicePhoneLayout);
        viewList.add(bean);

        //初始化唐诗
        RelativeLayout tangTitle = (RelativeLayout) view.findViewById(R.id.listen_tang_poetry_title);
        final View tangPoetryLayout = view.findViewById(R.id.tang_poetry_layout);
        bean = new ViewBean();
        bean.setViewTitle(tangTitle);
        bean.setViewLayout(tangPoetryLayout);
        viewList.add(bean);

        //初始化笑话
        RelativeLayout jokeTitle = (RelativeLayout) view.findViewById(R.id.say_joke_title);
        final View jokeLayout = view.findViewById(R.id.jokey_layout);
        bean = new ViewBean();
        bean.setViewTitle(jokeTitle);
        bean.setViewLayout(jokeLayout);
        viewList.add(bean);

        //初始化美女
        RelativeLayout beautyTitle = (RelativeLayout) view.findViewById(R.id.see_beauty_title);
        final View beautyLayout = view.findViewById(R.id.beauty_layout);
        bean = new ViewBean();
        bean.setViewTitle(beautyTitle);
        bean.setViewLayout(beautyLayout);
        viewList.add(bean);

        //初始化应用
        RelativeLayout appTitle = (RelativeLayout) view.findViewById(R.id.find_app_title);
        final View appLayout = view.findViewById(R.id.app_layout);
        bean = new ViewBean();
        bean.setViewTitle(appTitle);
        bean.setViewLayout(appLayout);
        viewList.add(bean);

        setAnimation(viewList);
    }

    private void setAnimation(List<ViewBean> viewList) {
        //给title设置点击事件，但是执行动画的是title对应的layout
        for (ViewBean bean : viewList) {
            final View viewTitle = bean.getViewTitle();
            final View viewLayout = bean.getViewLayout();
            viewTitle.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //给title对应的layout执行动画的操作
                    executeAnimation(viewTitle,viewLayout);
                }
            });
        }
        for (ViewBean bean : viewList) {
            LinearLayout viewLayout = (LinearLayout) bean.getViewLayout();
            int count = viewLayout.getChildCount();
            for (int i = 0; i < count; i ++) {
                viewLayout.getChildAt(i).setOnClickListener(viewLayoutClickListener);
            }
        }
    }
    private View.OnClickListener viewLayoutClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            String text = tv.getText().toString();
            String action = Constants.NAVIGATION_STRING_ACTION;
            String key = Constants.NAVIGATION_STRING_ACTION_KEY;
            BroadcastHelper.sendBroadCast(activity, action, key, text);
            sm.toggle();//关闭slidingmenu
        }
    };
    /**
     * 执行动画
     * @param viewLayout
     */
    private void executeAnimation(View viewTitle,View viewLayout) {
        /**
         * 执行动画的view如果是隐藏状态，自动展开，展开后将其设置为可见状态
         * 执行动画的view如果是可见状态，自动隐藏，隐藏后将其设置为不可见状态。
         */
        if (animState) { //表示当前可以执行动画
            ExpandAnimation anim = new ExpandAnimation(viewLayout, 300);
            boolean toggle = anim.toggle();
            if (toggle) {//表示当前动画已经展开
                //设置当前layout对应的title为按下状态
                viewTitle.setBackgroundResource(R.drawable.business_hall_selector_pressed);//将当前title设置为按下的效果
                RelativeLayout title = (RelativeLayout)viewTitle;
                //获得title的第1个孩子的第0个孩子的title
                LinearLayout ll = (LinearLayout) title.getChildAt(1);
                TextView myTitle = (TextView) ll.getChildAt(0);

                ImageView icon = (ImageView) title.getChildAt(0);//拿到title的第0个孩子，imageview
                ImageView arrow = (ImageView) title.getChildAt(2);//拿到title的箭头图标
                arrow.setImageResource(R.drawable.arrows_right_press);
                setPress(icon,myTitle);
                /**
                 * 如果lastView == null
                 * 说明之前没有执行过动画
                 * != null ?
                 * 说明执行过动画，再执行动画的时候需要将lastview给收回，收回之后再执行当前的动画
                 */
                if (lastView == null) {
                    lastView = viewLayout;
                } else {
                    if (lastView == viewLayout) {
                        lastView = null;
                    } else {
                        executeAnimation(viewTitle, lastView);
                        lastView = viewLayout;
                    }
                }
            } else {//当前动画已经关闭
                /**
                 * 打开第二个动画的时候，需要将上一个viewLayout(lastView)对应的title恢复为正常的状态
                 */
                for (ViewBean bean : viewList) {
                    if (lastView == bean.getViewLayout()) {//找到lastView对应的那个viewbean
                        RelativeLayout myTitle = (RelativeLayout)bean.getViewTitle();
                        myTitle.setBackgroundResource(R.drawable.business_hall_selector_normall);//lastView对应title背景恢复为默认
                        ImageView myArrow = (ImageView) myTitle.getChildAt(2);
                        myArrow.setImageResource(R.drawable.arrows_right_normal);//将lastview对应title的箭头设置为默认灰色

                        ImageView myIcon = (ImageView) myTitle.getChildAt(0);//lastview对应title的左侧图标

                        LinearLayout myLL = (LinearLayout) myTitle.getChildAt(1);
                        TextView myTv = (TextView) myLL.getChildAt(0);
                        setNormal(myIcon,myTv);

                        if (lastView == bean.getViewLayout()) {
                            lastView = null;
                        }
                    }
                }

                //将title设置为恢复正常状态
//				RelativeLayout title = (RelativeLayout) viewTitle;
//				title.setBackgroundResource(R.drawable.business_hall_selector_normall);
//				ImageView icon = (ImageView) title.getChildAt(0);//左侧图标
//				ImageView arrow = (ImageView) title.getChildAt(2);//拿到箭头
//				arrow.setImageResource(R.drawable.arrow_right);//设置为默认的灰色箭头
//
//				//拿到title第1个孩子的第0个孩子，TextView
//				LinearLayout ll = (LinearLayout) title.getChildAt(1);
//				TextView tv = (TextView) ll.getChildAt(0);
//
//				setNormal(icon,tv);

            }
            viewLayout.startAnimation(anim);
            anim.setAnimationListener(animationListener);
        } else {
            Log.d(TAG, "can't play animation");
        }


    }
    private void setNormal(ImageView img, TextView text) {
        String str = text.getText().toString();
        if (!TextUtils.isEmpty(str)) {
            if (str.contains(activity.getString(R.string.business_hall))) {
                img.setImageResource(R.drawable.find_businesshall);
            } else if (str.contains(activity.getString(R.string.international_roaming))) {
                img.setImageResource(R.drawable.internation_roaming);
            } else if (str.contains(activity.getString(R.string.mobile_mobile))) {
                img.setImageResource(R.drawable.find_cellphone);
            } else if (str.contains(activity.getString(R.string.packge_name))) {
                img.setImageResource(R.drawable.mobile_package);
            } else if (str.contains(activity.getString(R.string.telephone_charge))) {
                img.setImageResource(R.drawable.query_fee);
            } else if (str.contains(activity.getString(R.string.service_phone_number))) {
                img.setImageResource(R.drawable.call_customer_service_telephone);
            } else if (str.contains(activity.getString(R.string.tang_poetry))) {
                img.setImageResource(R.drawable.tang_poetry);
            } else if (str.contains(activity.getString(R.string.joke))) {
                img.setImageResource(R.drawable.joke);
            } else if (str.contains(activity.getString(R.string.myapp))) {
                img.setImageResource(R.drawable.app_btn_normal);
            } else if (str.contains(activity.getString(R.string.mm))) {
                img.setImageResource(R.drawable.online_service_normal);
            }

        }
    }

    private void setPress(ImageView img, TextView tv) {
        String str = tv.getText().toString();
        if (!TextUtils.isEmpty(str)) {
            if (str.contains(activity.getString(R.string.business_hall))) {//设置营业厅
                img.setImageResource(R.drawable.find_businesshall_press);
            } else if (str.contains(activity.getString(R.string.international_roaming))) {//国际漫游
                img.setImageResource(R.drawable.internation_roaming_press);
            } else if (str.contains(activity.getString(R.string.mobile_mobile))) {//手机
                img.setImageResource(R.drawable.find_cellphone_press);
            } else if (str.contains(activity.getString(R.string.packge_name))) {//套餐
                img.setImageResource(R.drawable.mobile_package_press);
            } else if (str.contains(activity.getString(R.string.telephone_charge))) {//查询余额
                img.setImageResource(R.drawable.query_fee_press);
            } else if (str.contains(activity.getString(R.string.service_phone_number))) {//客户电话
                img.setImageResource(R.drawable.call_customer_service_telephone_press);
            } else if (str.contains(activity.getString(R.string.tang_poetry))) {//背唐诗
                img.setImageResource(R.drawable.tang_poetry_press);
            } else if (str.contains(activity.getString(R.string.joke))) {//讲笑话
                img.setImageResource(R.drawable.joke_press);
            } else if (str.contains(activity.getString(R.string.myapp))) {//找应用
                img.setImageResource(R.drawable.app_btn_pressed);
            } else if (str.contains(activity.getString(R.string.mm))) {//找美女
                img.setImageResource(R.drawable.online_service_pressed);
            }
        }
    }
    Animation.AnimationListener animationListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            animState = true;
        }
        @Override
        public void onAnimationStart(Animation animation) {
            animState = false;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

}
