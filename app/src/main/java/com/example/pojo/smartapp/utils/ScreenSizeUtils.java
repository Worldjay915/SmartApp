package com.example.pojo.smartapp.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.utils
 * 创建者:  zsj
 * 创建事件: 2017/4/14 16:42
 * 描述:   获取屏幕高度和宽度
 */

public class ScreenSizeUtils {

    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }
}
