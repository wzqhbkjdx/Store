package com.cgtrc.bym.a10001store.utils;

import android.content.Context;
import android.content.Intent;

public class BroadcastHelper {

	/**
	 * ��������ʶ�����Ĺ㲥
	 * @param context
	 * @param content
	 */
	public static void sendVoiceBroadCast(Context context,String content) {
		Intent intent = new Intent();
        intent.setAction(Constants.VOICE_RECOGNITION_RESULT_ACTION);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(Constants.VOICE_RECOGNITION_RESULT_KEY, content);
        context.sendBroadcast(intent);
	}
	
	/**
	 * ����String ���͵�ֵ�Ĺ㲥
	 * @param context
	 * @param action
	 * @param key
	 * @param value
	 */
	public static void sendBroadCast(Context context,String action,String key,String value) {
		Intent intent = new Intent();
        intent.setAction(action);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(key, value);
        context.sendBroadcast(intent);
	}
	
	public static void sendBroadCast(Context context,String action,String key,int value) {
		Intent intent = new Intent();
		intent.setAction(action);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putExtra(key, value);
		context.sendBroadcast(intent);
	}
	
//	public static void toPage(Context context,int index) {
//		String action = Constants.WHERE_PAGER_ACTION;
//		String key = Constants.WHERE_PAGER_KEY;
//		BroadcastHelper.sendBroadCast(context, action, key, index);
//	}
}
