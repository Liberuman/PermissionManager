package com.sxu.permission;

import android.content.Context;

/*******************************************************************************
 *
 * 非Android组件类中的方法申请权限时可通过此接口传递Context
 *
 * 说明：当不能通过方法的上下文获取Context对象时，无法动态申请权限，
 *      所以可通过实现此接口来传递Context对象。
 *
 * @author Freeman
 *
 * @date 2018/10/17
 *
 *******************************************************************************/
public interface OnContextListener {

	/**
	 * 获取Context对象
	 * @return
	 */
	Context getContext();
}
