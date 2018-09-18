package com.sxu.permissionmanager;

import android.Manifest;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sxu.permission.CheckPermission;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				openCamera();
			}
		});
	}

	@CheckPermission(permissions = {Manifest.permission.CAMERA}, permissionDesc = "没有权限无法使用相机", settingDesc = "快去设置中开启权限")
	public void openCamera() {
		startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
	}

	@CheckPermission(permissions = {Manifest.permission.CAMERA})
	public void openCamera2() {
		startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
	}
}
