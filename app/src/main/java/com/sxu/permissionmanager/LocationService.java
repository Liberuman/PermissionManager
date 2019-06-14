package com.sxu.permissionmanager;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

/*******************************************************************************
 *
 * 在Service中测试动态申请权限
 *
 * @author Freeman
 *
 * @date 2019/06/14
 *
 *******************************************************************************/
public class LocationService extends IntentService {

	public LocationService() {
		super("LocationService");
	}

	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		Event.staticFuncTest(this);
	}
}
