package com.sxu.permissionmanager;

import android.Manifest;
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

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				call(MainActivity.this);
				Toast.makeText(MainActivity.this, "执行了", Toast.LENGTH_SHORT).show();
			}
		});

		Example example = new Example();
		example.startLocation(this);
		//call(this);
	}

	@CheckPermission(permissions = {Manifest.permission.CAMERA}, permissionDesc = "没有权限无法使用相机", settingDesc = "快去设置中开启权限")
	public void openCamera() {
		startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
	}

	@CheckPermission(permissions = {Manifest.permission.CAMERA})
	public void openCamera2() {
		startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
	}

	@CheckPermission(permissions = {Manifest.permission.CALL_PHONE}, permissionDesc = "", isBlock = "0")
	public static void call(Context context) {
		Toast.makeText(context, "开始打电话", Toast.LENGTH_SHORT).show();
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
