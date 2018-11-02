package com.sxu.permissionmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContainerFragment extends Fragment {

	private View containerLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		containerLayout =  inflater.inflate(R.layout.fragment_container_layout, container, false);
		return containerLayout;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
		transaction.add(R.id.container_layout, new InnerFragment());
		transaction.commit();
	}
}
