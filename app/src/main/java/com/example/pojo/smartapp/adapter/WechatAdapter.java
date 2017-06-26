package com.example.pojo.smartapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.entity.WechatData;
import com.example.pojo.smartapp.utils.PicassoUtils;
import com.example.pojo.smartapp.utils.ScreenSizeUtils;

import java.util.List;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.adapter
 * 创建者:  zsj
 * 创建事件: 2017/4/14 11:13
 * 描述:
 */

public class WechatAdapter extends BaseAdapter {

    private Context mContext;
    private List<WechatData> mList;
    private LayoutInflater inflater;
    private  WechatData data;

    public WechatAdapter(Context mContext,List<WechatData> mList){
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
            convertView = inflater.inflate(R.layout.wechat_item,null);
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tv_source = (TextView) convertView.findViewById(R.id.tv_source);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //获取屏幕宽高
        int[] screenSize = ScreenSizeUtils.getScreenSize(mContext);
        int width = screenSize[0];
        int height = screenSize[1];

        data = mList.get(position);
        if (!TextUtils.isEmpty(data.getImgUrl())){
            PicassoUtils.loadImageViewBySize(mContext,data.getImgUrl(),width/3,height/7,viewHolder.iv_img);
        }
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());

        return convertView;
    }


    class ViewHolder{

        private ImageView iv_img;
        private TextView tv_title;
        private  TextView tv_source;
    }


}
