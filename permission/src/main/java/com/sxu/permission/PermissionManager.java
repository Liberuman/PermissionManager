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
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*******************************************************************************
 * Description: 动态申请权限
 *
 * Author: Freeman
 *
 * Date: 2018/6/10
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/
public class PermissionManager {

    private final static int PERMISSION_REQUEST_CODE = 0x1001;
    private String explainDesc;
    private String permissionSettingDesc;
    private List<Integer> permissionStatusList = new LinkedList<>(); // 解决请求权限回调结果不准确的问题
    private AlertStyle alertStyle;
    private OnPermissionRequestListener requestListener;

    private PermissionManager() {
        alertStyle = new DefaultAlertStyle();
    }

    public static PermissionManager getInstance() {
        return Singleton.instance;
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
    public static boolean hasPermission(@NonNull Context context, @NonNull String[] permissions) {
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
    public void requestPermission(@NonNull Activity context, @NonNull String[] permissions) {
        permissionStatusList.clear();
        List<String> refusedPermission = new ArrayList<>();
        for (int i = 0, size = permissions.length; i < size; i++) {
            permissionStatusList.add(PermissionChecker.checkSelfPermission(context, permissions[i]));
            if (permissionStatusList.get(i) != PackageManager.PERMISSION_GRANTED) {
                refusedPermission.add(permissions[i]);
            }
        }

        int refusedPermissionSize = refusedPermission.size();
        if (refusedPermissionSize > 0) {
            ActivityCompat.requestPermissions(context, refusedPermission.toArray(new String[refusedPermissionSize]),
                    PERMISSION_REQUEST_CODE);
        } else {
            if (requestListener != null) {
                requestListener.onGranted(context);
            }
        }
    }

    /**
     * 用于Fragment申请权限
     * @param fragment
     * @param permissions
     */
    public void requestPermission(@NonNull Fragment fragment, @NonNull String[] permissions) {
        permissionStatusList.clear();
        List<String> refusedPermission = new ArrayList<>();
        for (int i = 0, size = permissions.length; i < size; i++) {
            permissionStatusList.add(PermissionChecker.checkSelfPermission(fragment.getActivity(), permissions[i]));
            if (permissionStatusList.get(i) != PackageManager.PERMISSION_GRANTED) {
                refusedPermission.add(permissions[i]);
            }
        }

        int refusedPermissionSize = refusedPermission.size();
        if (refusedPermissionSize > 0) {
            fragment.requestPermissions(refusedPermission.toArray(new String[refusedPermissionSize]),
                    PERMISSION_REQUEST_CODE);
        } else {
            if (requestListener != null) {
                requestListener.onGranted(fragment.getActivity());
            }
        }
    }

    public void requestCallback(final Activity context, int requestCode, String permissions[], int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }

        int refusedPermissionIndex = -1;
        for (int i = 0, size = permissions.length; i < size; i++) {
            //grantResults[i] = permissionStatusList.get(i);
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED
                    || permissionStatusList.get(i) != PackageManager.PERMISSION_GRANTED) {
                grantResults[i] = PermissionChecker.checkSelfPermission(context, permissions[i]);
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (refusedPermissionIndex == -1) {
                        refusedPermissionIndex = i;
                    }
                }
            } else {
                if (refusedPermissionIndex == -1) {
                    refusedPermissionIndex = i;
                }
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
        this.alertStyle = this.alertStyle;
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
        private final static PermissionManager instance = new PermissionManager();
    }
}