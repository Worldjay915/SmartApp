package com.example.pojo.smartapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pojo.smartapp.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.utils
 * 创建者:  zsj
 * 创建事件: 2017/4/10 15:27
 * 描述:  工具统一类
 */

public class UtilTools {
   //设置字体
    public  static void setFonts(Context mContext, TextView textView){
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(typeface);
    }

    //将图片保存到SharePreferences
    public static void  putImageToSharePre(Context mContext, ImageView imageView){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte byteArr[] = baos.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArr,Base64.DEFAULT));
        ShareUtils.putString(mContext,"image_head",imageString);
    }

    public static void  getImageFromSharePre(Context mContext,ImageView imageView){
        String  imageString = ShareUtils.getString(mContext,"image_head","");
        if (!imageString.equals("")){
            byte[] bytes = Base64.decode(imageString, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            Bitmap bitmap = BitmapFactory.decodeStream(bais);
            imageView.setImageBitmap(bitmap);
        }
    }

    //获取版本号
    public static String getVersion(Context mContext){
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(mContext.getPackageName(),0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return mContext.getString(R.string.text_unknown);
        }
    }

}
