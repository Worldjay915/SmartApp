package com.example.pojo.smartapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.adapter.DeliveryAdapter;
import com.example.pojo.smartapp.entity.DeliveryData;
import com.example.pojo.smartapp.utils.L;
import com.example.pojo.smartapp.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/12 19:29
 * 描述:  快递查询
 */

public class DeliveryActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_company;
    private EditText et_number;
    private Button   btn_query;
    private ListView mListView;
    private List<DeliveryData> mList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        initView();

    }

    private void initView() {
        et_company = (EditText) findViewById(R.id.et_company);
        et_number  = (EditText) findViewById(R.id.et_number);
        btn_query  = (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.mListView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_query:
                String company = et_company.getText().toString().trim();
                String number  = et_number.getText().toString().trim();
                //http://v.juhe.cn/exp/index?key=7be2c01b37ed39abe7e931c521118f7e&com=sto&no=3326339642045
                String  url = "http://v.juhe.cn/exp/index?key="+ StaticClass.DELIVERY_APPID+
                        "&com="+company+"&no="+number;
                //判断是否为空
                if(!TextUtils.isEmpty(company)&!TextUtils.isEmpty(number)){
                 //拼接查询接口
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            parseJson(t);
                        }
                    });

                }else {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void parseJson(String t) {
        L.i("物流查询----"+t.toString());
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray  jsonArray  = jsonResult.getJSONArray("list");
            for (int i = 0;i< jsonArray.length(); i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                DeliveryData deliveryData = new DeliveryData();
                deliveryData.setRemark(json.getString("remark"));
                deliveryData.setZone(json.getString("zone"));
                deliveryData.setDatetime(json.getString("datetime"));
                mList.add(deliveryData);
            }
            //倒序
            Collections.reverse(mList);
            DeliveryAdapter adapter = new DeliveryAdapter(this,mList);
            mListView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
