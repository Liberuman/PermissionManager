package com.sxu.permission;

import android.content.Context;

/*******************************************************************************
 * Description: 非Android组件类中的方法申请权限时需实现此接口
 *
 *  非Android组件类中的方法因不能获取Context对象而无法申请权限，
 *  所以可通过实现此接口来实现权限申请。
 *
 * Author: Freeman
 *
 * Date: 2018/10/17
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public interface OnContextListener {

	Context getContext();
}
