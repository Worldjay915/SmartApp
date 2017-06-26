package com.example.pojo.smartapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.adapter.WechatAdapter;
import com.example.pojo.smartapp.entity.WechatData;
import com.example.pojo.smartapp.ui.WebViewActivity;
import com.example.pojo.smartapp.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.fragment
 * 创建者:  zsj
 * 创建事件: 2017/4/10 16:30
 * 描述:
 */

public class WechatFragment extends Fragment{

    private ListView mListView;
    private List<WechatData> mList = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();
    private List<String> mUrl = new ArrayList<>();
    private WechatAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wechat_fragment,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);
        String url ="http://v.juhe.cn/weixin/query?key="+ StaticClass.WECHAT_APPID+"&ps=50";

        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {

                //L.i("wechat Json:" + t);
                parseJson(t);

            }
        });

        // 点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("title",mTitle.get(position));
                intent.putExtra("url",mUrl.get(position));
                startActivity(intent);
            }
        });


    }

    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray  jsonArray  = jsonResult.getJSONArray("list");
            for (int i = 0;i <jsonArray.length();i++){
                WechatData wechatData = new WechatData();
                JSONObject json = (JSONObject) jsonArray.get(i);
                String title = json.getString("title");
                String url = json.getString("url");
                wechatData.setTitle(title);
                wechatData.setSource(json.getString("source"));
                wechatData.setImgUrl(json.getString("firstImg"));
               // L.i("firstImg"+wechatData.getImgUrl());
                mList.add(wechatData);
                mTitle.add(title);
                mUrl.add(url);
            }
            adapter = new WechatAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
