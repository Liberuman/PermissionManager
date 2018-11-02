package com.sxu.permissionmanager;

import android.Manifest;
import android.content.Context;
import android.widget.Toast;

import com.sxu.permission.CheckPermission;
import com.sxu.permission.OnContextListener;

/*******************************************************************************
 * Description: 在普通的JAVA类中使用权限注解
 *
 * Author: Freeman
 *
 * Date: 2018/11/2
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class CommonBean implements OnContextListener {

	private Context context;

	public CommonBean(Context context) {
		this.context = context;
	}

	@Override
	public Context getContext() {
		return context;
	}

	@CheckPermission(permissions = {Manifest.permission.ACCESS_FINE_LOCATION})
	public void startLocation() {
		Toast.makeText(context, "普通类中使用注解成功获取权限啦", Toast.LENGTH_LONG).show();
	}
}
