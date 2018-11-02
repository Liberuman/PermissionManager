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

	private Event event;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		event = new Event(this);

		findViewById(R.id.location_button).setOnClickListener(this);
		findViewById(R.id.phone_button).setOnClickListener(this);
		findViewById(R.id.sms_button).setOnClickListener(this);
		findViewById(R.id.file_button).setOnClickListener(this);
		findViewById(R.id.other_button).setOnClickListener(this);
		findViewById(R.id.no_block_button).setOnClickListener(this);
		findViewById(R.id.fragment_button).setOnClickListener(this);
		findViewById(R.id.nestling_fragment_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		view.setVisibility(View.VISIBLE);
		switch (view.getId()) {
			case R.id.location_button:
				event.startLocation();
				break;
			case R.id.phone_button:
				event.startCall();
				break;
			case R.id.sms_button:
				event.openSms();
				break;
			case R.id.file_button:
				event.openExternalStorage();
				break;
			case R.id.other_button:
				event.requestMultiPermission();
				break;
			case R.id.fragment_button:
				ContainerActivity.enter(this, false);
				break;
			case R.id.nestling_fragment_button:
				ContainerActivity.enter(this, true);
				break;
			case R.id.no_block_button:
				event.noBlock();
				break;
			default:
				break;
		}
	}
}
