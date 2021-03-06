package com.sxu.permission;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/*******************************************************************************
 *
 * 权限切面的实现
 *
 * @author Freeman
 *
 * @date 2018/8/17
 *
 *******************************************************************************/
@Aspect
public class PermissionAspect {

	@Pointcut("execution(@com.sxu.permission.CheckPermission * *(..))")
	public void executionCheckPermission() {

	}

	@Around("executionCheckPermission()")
	public void checkPermission(final ProceedingJoinPoint joinPoint) {
		Object object = joinPoint.getThis();
		Context context = getContext(object);
		// 静态方法从参数中获取Context对象
		if (context == null) {
			Object[] args = joinPoint.getArgs();
			if (args == null || args.length == 0) {
				return;
			}
			for (int i = 0, size = args.length; i < size; i++) {
				context = getContext(args[i]);
				if (context != null) {
					break;
				}
			}
			if (context == null) {
				return;
			}
		}

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature != null ? signature.getMethod() : null;
		if (method == null) {
			return;
		}
		final CheckPermission checkPermission = method.getAnnotation(CheckPermission.class);
		if (checkPermission == null) {
			return;
		}
		PermissionActivity.enter(context, checkPermission.permissions(), checkPermission.permissionDesc(),
				checkPermission.settingDesc(), new OnPermissionRequestListener() {
					@Override
					public void onGranted(Activity context) {
						try {
							joinPoint.proceed();
						} catch (Throwable throwable) {
							throwable.printStackTrace(System.err);
						}
						context.finish();
					}

					@Override
					public void onAsked(Activity context) {
						if (!CheckPermission.DEFAULT_IS_BLOCK_VALUE.equals(checkPermission.isBlock())) {
							try {
								joinPoint.proceed();
							} catch (Throwable throwable) {
								throwable.printStackTrace(System.err);
							}
						}
						context.finish();
					}

					@Override
					public void onDenied(Activity context) {
						if (!CheckPermission.DEFAULT_IS_BLOCK_VALUE.equals(checkPermission.isBlock())) {
							try {
								joinPoint.proceed();
							} catch (Throwable throwable) {
								throwable.printStackTrace(System.err);
							}
						}
					}
				});
	}

	private Context getContext(Object object) {
		Context context = null;
		if (object instanceof OnContextListener) {
			context = ((OnContextListener) object).getContext();
		} else if (object instanceof Activity || object instanceof Application || object instanceof Service) {
			context = (Context) object;
		} else if (object instanceof ContentProvider) {
			context = ((ContentProvider) object).getContext();
		} else if (object instanceof Fragment) {
			context = ((Fragment) object).getActivity();
		} else if (object instanceof android.support.v4.app.Fragment) {
			context = ((android.support.v4.app.Fragment) object).getActivity();
		} else if (object instanceof Dialog) {
			context = ((Dialog) object).getContext();
		} else if (object instanceof View) {
			context = ((View) object).getContext();
		}

		return context;
	}
}
