package com.sxu.permission;

import android.app.Activity;
import android.content.Context;

/*******************************************************************************
 * Description: 自定义解释申请权限和引导用户开启权限的样式
 *
 * Author: Freeman
 *
 * Date: 2018/11/1
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public interface AlertStyle {

	void onExplain(Activity context, String reason);

	void onSettingGuide(Activity context, String settingDesc);
}
