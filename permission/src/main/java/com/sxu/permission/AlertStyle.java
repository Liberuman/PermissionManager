package com.sxu.permission;

import android.app.Activity;

/*******************************************************************************
 * 自定义解释申请权限和引导用户开启权限的样式
 *
 * @author Freeman
 *
 * @date 2018/11/1
 *
 *******************************************************************************/
public interface AlertStyle {

	/**
	 * 实现解释权限原因的样式
	 * @param context
	 * @param reason 原因说明
	 */
	void onExplain(Activity context, String reason);

	/**
	 * 实现引导用户去开启权限的样式
	 * @param context
	 * @param settingDesc 引导设置权限的描述
	 */
	void onSettingGuide(Activity context, String settingDesc);
}
