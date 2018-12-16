package com.sxu.permissionmanager;

import android.Manifest;
import android.content.Context;
import android.widget.Toast;

import com.sxu.permission.CheckPermission;
import com.sxu.permission.OnContextListener;
import com.sxu.permission.PermissionManager;

/*******************************************************************************
 * Description: 申请权限的测试用例，在非Android组件的类中使用注解时需实现OnContextListener接口
 *
 * Author: Freeman
 *
 * Date: 2018/11/2
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class Event implements OnContextListener {
	
	private Context context;
	
	public Event(Context context) {
		this.context = context;
	}

	@Override
	public Context getContext() {
		return context;
	}

	@CheckPermission(permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
			permissionDesc = "没有权限无法定位", settingDesc = "快去设置中开启权限")
	public void startLocation() {
		Toast.makeText(context, "已获取权限，开始定位~", Toast.LENGTH_LONG).show();
	}

	@CheckPermission(permissions = {Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.CALL_PHONE, Manifest.permission.USE_SIP},
			permissionDesc = "没有权限无法定位", settingDesc = "快去设置中开启权限")
	public void startCall() {
		Toast.makeText(context, "已获取权限，开始打电话~", Toast.LENGTH_LONG).show();
	}

	@CheckPermission(permissions = {Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
			Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_WAP_PUSH,
			Manifest.permission.RECEIVE_MMS})
	public void openSms() {
		Toast.makeText(context, "已获取权限，访问短信~", Toast.LENGTH_LONG).show();
	}

	@CheckPermission(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
	public void openExternalStorage() {
		Toast.makeText(context, "已获取权限，访问存储空间~", Toast.LENGTH_LONG).show();
	}

	@CheckPermission(permissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
			Manifest.permission.READ_CALL_LOG})
	public void requestMultiPermission() {
		Toast.makeText(context, "已获取不同权限组的多个权限~", Toast.LENGTH_LONG).show();
	}

	@CheckPermission(permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
			permissionDesc = "", settingDesc = "", isBlock = "0")
	public void noBlock() {
		if (PermissionManager.hasPermission(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
				Manifest.permission.ACCESS_COARSE_LOCATION})) {
			// todo 需要权限才能执行的逻辑
		}

		Toast.makeText(context, "未获取权限，代码继续执行", Toast.LENGTH_LONG).show();
	}


	@CheckPermission(permissions = {Manifest.permission.ACCESS_FINE_LOCATION}, permissionDesc = "没有权限无法定位", settingDesc = "快去设置中开启定位权限")
	public static void staticFuncTest(int a, Context context) {
		Toast.makeText(context, "静态方法中使用注解权限获取成功~", Toast.LENGTH_SHORT).show();
	}


}
