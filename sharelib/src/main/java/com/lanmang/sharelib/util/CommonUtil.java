package com.lanmang.sharelib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;
import java.util.Map;

/**
 * Created by lanmang on 2017/1/17.
 */

public class CommonUtil {

    /**
     * 判断应用是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void isInstalled(Context context, Map<String, Boolean> map) {
        try {
            List<PackageInfo> infos = context.getPackageManager().getInstalledPackages(0);
            for (PackageInfo info : infos) {
                String packageName = info.packageName;
                Boolean aBoolean = map.get(packageName);
                if (aBoolean != null) {
                    map.put(packageName, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
