package com.sxu.permission;

import android.app.Activity;
import android.content.Context;

/*******************************************************************************
 *
 * 权限申请的监听接口
 *
 * @author Freeman
 *
 * @date 2018/11/1
 *
 *******************************************************************************/
public interface OnPermissionRequestListener {

	/**
	 * 已获取权限时被调用
	 * @param context
	 */
	void onGranted(Activity context);

	/**
	 * 向用户申请权限时调用
	 * @param context
	 */
	void onAsked(Activity context);

	/**
	 * 申请权限被拒绝后调用
	 * @param context
	 */
	void onDenied(Activity context);
}
