/*
* Copyright 2015 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.sxu.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*******************************************************************************
 *
 * 动态申请权限
 *
 * @author Freeman
 *
 * @date 2018/6/10
 *
 *******************************************************************************/
public class PermissionManager {

    private final static int PERMISSION_REQUEST_CODE = 0x1001;
    private String explainDesc;
    private String permissionSettingDesc;
    private AlertStyle alertStyle;
    private OnPermissionRequestListener requestListener;

    private PermissionManager() {
        alertStyle = new DefaultAlertStyle();
    }

    public static PermissionManager getInstance() {
        return Singleton.INSTANCE;
    }

    /**
     * 是否已获取指定权限
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermission(@NonNull Context context, @NonNull String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
                || PermissionChecker.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }

    /**
     * 是否已获取指定权限
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermission(@NonNull Context context, String[] permissions) {
        if (permissions == null ||  permissions.length == 0) {
            return true;
        }

        for (String item : permissions) {
            if (ContextCompat.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED
                    || PermissionChecker.checkSelfPermission(context, item) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 用于Activity申请权限
     * @param context
     * @param permissions
     */
    public void requestPermission(@NonNull Activity context, String[] permissions) {
	    realRequestPermission(context, permissions);
    }

    /**
     * 用于Fragment申请权限
     * @param fragment
     * @param permissions
     */
    public void requestPermission(@NonNull Fragment fragment, String[] permissions) {
        realRequestPermission(fragment, permissions);
    }

	/**
	 * 申请权限
	 * @param object
	 * @param permissions
	 */
	private void realRequestPermission(@NonNull Object object, String[] permissions) {
    	Activity activity = object instanceof Activity ? (Activity) object : ((Fragment)object).getActivity();
	    if (permissions == null ||  permissions.length == 0) {
		    if (requestListener != null) {
			    requestListener.onGranted(activity);
		    }
		    return;
	    }

	    List<String> refusedPermission = new LinkedList<>();
	    for (int i = 0, size = permissions.length; i < size; i++) {
		    int permissionStatus = PermissionChecker.checkSelfPermission(activity, permissions[i]);
		    if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
			    refusedPermission.add(permissions[i]);
		    }
	    }
	    int refusedPermissionSize = refusedPermission.size();
	    if (refusedPermissionSize > 0) {
	    	if (object instanceof Activity) {
			    ActivityCompat.requestPermissions(activity,
				    refusedPermission.toArray(new String[refusedPermissionSize]), PERMISSION_REQUEST_CODE);
		    } else {
			    ((Fragment)object).requestPermissions(refusedPermission.toArray(new String[refusedPermissionSize]),
				    PERMISSION_REQUEST_CODE);
		    }
	    } else {
		    if (requestListener != null) {
			    requestListener.onGranted(activity);
		    }
	    }
    }

    public void requestCallback(final Activity context, int requestCode, String []permissions, int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }

        int refusedPermissionIndex = -1;
        for (int i = 0, size = permissions.length; i < size; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
	            refusedPermissionIndex = i;
	            break;
            }
        }

        if (refusedPermissionIndex != -1) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[refusedPermissionIndex])) {
                alertStyle.onExplain(context, explainDesc);
                if (requestListener != null) {
                    requestListener.onAsked(context);
                }
            } else {
                alertStyle.onSettingGuide(context, permissionSettingDesc);
                if (requestListener != null) {
                    requestListener.onDenied(context);
                }
            }
        } else {
            if (requestListener != null) {
                requestListener.onGranted(context);
            }
        }
    }

    public PermissionManager setAlertStyle(@NonNull AlertStyle alertStyle) {
        this.alertStyle = alertStyle;
        return this;
    }

    public PermissionManager setExplainDesc(String desc) {
        this.explainDesc = desc;
        return this;
    }

    public PermissionManager setSettingDesc(String settingDesc) {
        this.permissionSettingDesc = settingDesc;
        return this;
    }

    public PermissionManager setPermissionRequestListener(OnPermissionRequestListener listener) {
        this.requestListener = listener;
        return this;
    }

    public void setParams(String desc, String settingDesc, OnPermissionRequestListener listener) {
        this.explainDesc = desc;
        this.permissionSettingDesc = settingDesc;
        this.requestListener = listener;
    }

    public static class Singleton {
        private final static PermissionManager INSTANCE = new PermissionManager();
    }
}