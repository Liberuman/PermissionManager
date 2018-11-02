package com.sxu.permissionmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

		containerLayout.findViewById(R.id.location_button).setOnClickListener(this);
		containerLayout.findViewById(R.id.phone_button).setOnClickListener(this);
		containerLayout.findViewById(R.id.sms_button).setOnClickListener(this);
		containerLayout.findViewById(R.id.file_button).setOnClickListener(this);
		containerLayout.findViewById(R.id.other_button).setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
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
			default:
				break;
		}
	}
}
