package com.sxu.permissionmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sxu.permission.CheckPermission;
import com.sxu.permission.OnContextListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.location_button).setOnClickListener(this);
		findViewById(R.id.phone_button).setOnClickListener(this);
		findViewById(R.id.sms_button).setOnClickListener(this);
		findViewById(R.id.file_button).setOnClickListener(this);
		findViewById(R.id.other_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.location_button:
				startLocation();
				break;
			case R.id.phone_button:
				startCall();
				break;
			case R.id.sms_button:
				openSms();
				break;
			case R.id.file_button:
				openExternalStorage();
				break;
			case R.id.other_button:
				requestMultiPermission();
				break;
		}
	}

	@CheckPermission(permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
			permissionDesc = "没有权限无法定位", settingDesc = "快去设置中开启权限")
	public void startLocation() {
		Toast.makeText(this, "已获取权限，开始定位~", Toast.LENGTH_LONG).show();
	}

	@CheckPermission(permissions = {Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.CALL_PHONE, Manifest.permission.USE_SIP},
			permissionDesc = "没有权限无法定位", settingDesc = "快去设置中开启权限")
	public void startCall() {
		Toast.makeText(this, "已获取权限，开始打电话~", Toast.LENGTH_LONG).show();
	}

	@CheckPermission(permissions = {Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
			Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_WAP_PUSH,
			Manifest.permission.RECEIVE_MMS})
	public void openSms() {
		Toast.makeText(this, "已获取权限，访问短信~", Toast.LENGTH_LONG).show();
	}

	@CheckPermission(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
	public void openExternalStorage() {
		Toast.makeText(this, "已获取权限，访问存储空间~", Toast.LENGTH_LONG).show();
	}

	@CheckPermission(permissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
		Manifest.permission.BODY_SENSORS})
	public void requestMultiPermission() {
		Toast.makeText(this, "已获取不同权限组的多个权限~", Toast.LENGTH_LONG).show();
	}

	// 非Android组件类中请求权限实例
	public class Example implements OnContextListener {

		@CheckPermission(permissions = {Manifest.permission.ACCESS_FINE_LOCATION}, permissionDesc = "没有权限无法定位", settingDesc = "快去设置中开启定位权限")
		public void startLocation(Context context) {
			Toast.makeText(context, "开始定位", Toast.LENGTH_SHORT).show();
		}

		@Override
		public Context getContext() {
			return MainActivity.this;
		}
	}
}
