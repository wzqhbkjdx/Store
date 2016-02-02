package com.cgtrc.bym.a10001store.ui;

import android.util.Log;

import com.cgtrc.bym.a10001store.utils.Constants;


public class MyLog {

	public static void d(String tag,String msg) {
		if (Constants.IS_DEBUG) {
			Log.d(tag, msg);
		}
	}
	public static void d(String tag,String msg,Throwable e) {
		if (Constants.IS_DEBUG) {
			Log.d(tag, msg,e);
		}
	}
	public static void i(String tag,String msg) {
		if (Constants.IS_DEBUG) {
			Log.i(tag, msg);
		}
	}
	public static void v(String tag,String msg) {
		if (Constants.IS_DEBUG) {
			Log.v(tag, msg);
		}
	}
	public static void e(String tag,String msg) {
		if (Constants.IS_DEBUG) {
			Log.e(tag, msg);
		}
	}
	public static void e(String tag,String msg,Throwable e) {
		if (Constants.IS_DEBUG) {
			Log.e(tag, msg,e);
		}
	}
	public static void w(String tag,String msg) {
		if (Constants.IS_DEBUG) {
			Log.w(tag, msg);
		}
	}
	public static void w(String tag,String msg,Throwable e) {
		if (Constants.IS_DEBUG) {
			Log.w(tag, msg,e);
		}
	}
	
}
