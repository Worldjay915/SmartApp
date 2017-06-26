package com.example.pojo.smartapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.entity.GirlData;
import com.example.pojo.smartapp.utils.PicassoUtils;
import com.example.pojo.smartapp.utils.ScreenSizeUtils;

import java.util.List;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.adapter
 * 创建者:  zsj
 * 创建事件: 2017/4/14 16:27
 * 描述:  girl图片适配器
 */

public class GirlPhotoAdapter extends BaseAdapter{

    private Context mContext;
    private List<GirlData>  mList;
    private LayoutInflater inflater;
    private GirlData data;

    public GirlPhotoAdapter(Context mContext,List<GirlData>  mList){
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.girl_item,null);
            viewHolder.iv_girl = (ImageView) convertView.findViewById(R.id.iv_girl);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        //通过工具类获得 屏幕 宽和高
        int [] screenSize = ScreenSizeUtils.getScreenSize(mContext);
        int width = screenSize[0];
        int height = screenSize[1];

        PicassoUtils.loadImageViewBySize(mContext,data.getImgUrl(),width/2,height/2,viewHolder.iv_girl);

        return convertView;
    }


    class  ViewHolder{
        private ImageView iv_girl;
    }

}
