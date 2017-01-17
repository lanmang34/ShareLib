package com.lanmang.sharelib.util;

import android.content.Context;
import android.content.pm.PackageManager;

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
}
