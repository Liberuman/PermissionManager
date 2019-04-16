package com.sxu.permissionmanager;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sxu.permission.OnPermissionRequestListener;
import com.sxu.permission.PermissionManager;

/*******************************************************************************
 *
 * 嵌套Fragment申请权限示例页面
 *
 * @author Freeman
 *
 * @date 2018/11/2
 *
 *******************************************************************************/
public class InnerFragment extends Fragment implements View.OnClickListener {

	private View containerLayout;

	private Event event;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		containerLayout = inflater.inflate(R.layout.fragment_inner_layout, container, false);
		return containerLayout;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		event = new Event(getActivity());

		containerLayout.findViewById(R.id.no_annotation_button).setOnClickListener(this);

		containerLayout.findViewById(R.id.location_button).setOnClickListener(this);
		containerLayout.findViewById(R.id.phone_button).setOnClickListener(this);
		containerLayout.findViewById(R.id.sms_button).setOnClickListener(this);
		containerLayout.findViewById(R.id.file_button).setOnClickListener(this);
		containerLayout.findViewById(R.id.other_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.no_annotation_button:
				PermissionManager.getInstance().setParams("Fragment申请权限拒绝啦",
						"Fragment开启权限引导描述", new OnPermissionRequestListener() {
							@Override
							public void onGranted(Activity context) {
								Toast.makeText(context, "获取权限啦", Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onAsked(Activity context) {
								Toast.makeText(context, "权限被拒绝了", Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onDenied(Activity context) {
								Toast.makeText(context, "权限被彻底拒绝了", Toast.LENGTH_SHORT).show();
							}
						});
				PermissionManager.getInstance().requestPermission(this,
						new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
				break;
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
			default:
				break;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		PermissionManager.getInstance().requestCallback(getActivity(), requestCode, permissions, grantResults);
	}
}
