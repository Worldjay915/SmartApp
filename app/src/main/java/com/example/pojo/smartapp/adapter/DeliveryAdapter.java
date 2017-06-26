package com.example.pojo.smartapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.entity.DeliveryData;

import java.util.List;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.adapter
 * 创建者:  zsj
 * 创建事件: 2017/4/13 12:50
 * 描述:
 */

public class DeliveryAdapter  extends BaseAdapter {

    private Context mContext;
    private List<DeliveryData> mList;
    private LayoutInflater  mlayoutInflater;
    private DeliveryData data;

    public DeliveryAdapter(Context mContext,List<DeliveryData> mList){
        this.mContext = mContext;
        this.mList = mList;
        mlayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder  viewHolder = null;
        //判断是否为空
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mlayoutInflater.inflate(R.layout.delivery_item,null);
            viewHolder.tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = (TextView) convertView.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime = (TextView) convertView.findViewById(R.id.tv_datetime);
            //设置缓存
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        data = mList.get(position);
        viewHolder.tv_remark.setText(data.getRemark());
        viewHolder.tv_zone.setText(data.getZone());
        viewHolder.tv_datetime.setText(data.getDatetime());

        return convertView;
    }



    class  ViewHolder{
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;
    }
}
