package com.sxu.permissionmanager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ContainerActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_container_layout);

		boolean isNestling = getIntent().getBooleanExtra("isNestling", false);
		if (isNestling) {
			findViewById(R.id.no_annotation_button).setVisibility(View.GONE);
		}
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.container_layout, isNestling ? new ContainerFragment() : new InnerFragment());
		transaction.commit();
	}

	public static void enter(Context context, boolean isNestling) {
		Intent intent = new Intent(context, ContainerActivity.class);
		intent.putExtra("isNestling", isNestling);
		context.startActivity(intent);
	}
}
