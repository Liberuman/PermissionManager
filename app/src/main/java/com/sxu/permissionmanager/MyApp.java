package com.sxu.permissionmanager;

import android.app.Application;
import android.content.Intent;
import android.location.Location;
import android.widget.Toast;

/*******************************************************************************
 *
 * 在Application中测试动态申请权限
 *
 * @author Freeman
 *
 * @date 2019/06/14
 *
 *******************************************************************************/
public class MyApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		startService(new Intent(this, LocationService.class));
		//Event.staticFuncTest(this);
	}
}
