package com.sxu.permission;

import android.app.Activity;
import android.content.Context;

/*******************************************************************************
 * Description: 权限申请监听
 *
 * Author: Freeman
 *
 * Date: 2018/11/1
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public interface OnPermissionRequestListener {

	// 权限已获取
	void onGranted(Activity context);

	// 权限被询问
	void onAsked(Activity context);

	// 权限被拒绝
	void onDenied(Activity context);
}
