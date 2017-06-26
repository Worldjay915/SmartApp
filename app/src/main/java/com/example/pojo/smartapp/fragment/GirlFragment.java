package com.example.pojo.smartapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.adapter.GirlPhotoAdapter;
import com.example.pojo.smartapp.entity.GirlData;
import com.example.pojo.smartapp.utils.PicassoUtils;
import com.example.pojo.smartapp.utils.ScreenSizeUtils;
import com.example.pojo.smartapp.view.CustomDialog;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.fragment
 * 创建者:  zsj
 * 创建事件: 2017/4/10 16:30
 * 描述: girl 图片浏览页
 */

public class GirlFragment extends Fragment{

    private GridView mGridView;
    private GirlPhotoAdapter adapter;
    private List<GirlData> mList = new ArrayList<>();
    private List<String> mImgUrl  = new ArrayList<>();
    private CustomDialog mdialog;
    private PhotoView photoView;
    private PhotoViewAttacher attacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.girl_fragment,null);
        initView(view);
        return view;
    }

    private void initView(View view) {

        mGridView = (GridView) view.findViewById(R.id.mGridView);
        mdialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,R.layout.dialog_girl,
                R.style.Theme_dialog, Gravity.CENTER);
        photoView= (PhotoView) mdialog.findViewById(R.id.photo_view);

        String welfare = null;
        try {
            //Gank升級 需要转码
            welfare = URLEncoder.encode(new String("福利"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RxVolley.get("http://gank.io/api/search/query/listview/category/"+welfare+"/count/50/page/1", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //L.i("Girl Json:" + t);
                parseJson(t);
            }
        });
        //获取屏幕宽和高
        int[] screenSize = ScreenSizeUtils.getScreenSize(getActivity());
        final int width = screenSize[0];
        final int height = screenSize[1];

        //设置点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PicassoUtils.loadImageViewBySize(getActivity(),mImgUrl.get(position),width,height,photoView);
                //缩放
                attacher = new PhotoViewAttacher(photoView);
                //刷新
                attacher.update();
                mdialog.show();
            }
        });

    }

    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                mImgUrl.add(url);

                GirlData data = new GirlData();
                data.setImgUrl(url);
                mList.add(data);
            }
            adapter = new GirlPhotoAdapter(getActivity(),mList);
            mGridView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
