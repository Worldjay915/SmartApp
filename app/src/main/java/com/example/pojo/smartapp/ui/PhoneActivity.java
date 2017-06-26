package com.example.pojo.smartapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.utils.L;
import com.example.pojo.smartapp.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名: Sma rtApp
 * 包名: com.example.pojo.smartapp.ui
 * 创建者:  zsj
 * 创建事件: 2017/4/13 13:29
 * 描述:   电话归属地查询
 */

public class PhoneActivity  extends  BaseActivity implements View.OnClickListener {

    private EditText et_number;
    private ImageView  iv_company;
    private TextView tv_resule;
    private Button btn_1, btn_2,btn_3,btn_4,btn_5,btn_6,
            btn_7,btn_8,btn_9,btn_0,btn_del,btn_query;

    //标记位
    private boolean flag = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initView();
    }

    private void initView() {
        et_number = (EditText) findViewById(R.id.et_number);
        iv_company = (ImageView) findViewById(R.id.iv_company);
        tv_resule = (TextView) findViewById(R.id.tv_result);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_del =(Button) findViewById(R.id.btn_del);
        btn_query = (Button)findViewById(R.id.btn_query);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_query.setOnClickListener(this);

        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv_resule.setText("");
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {

        String str = et_number.getText().toString().trim();
        switch (v.getId()){
            case  R.id.btn_0:
            case  R.id.btn_1:
            case  R.id.btn_2:
            case  R.id.btn_3:
            case  R.id.btn_4:
            case  R.id.btn_5:
            case  R.id.btn_6:
            case  R.id.btn_7:
            case  R.id.btn_8:
            case  R.id.btn_9:
                if (flag) {
                    flag = false;
                    str = "";
                    et_number.setText("");
                }
                et_number.setText(str+((Button) v).getText());
                et_number.setSelection(str.length()+1);

                break;
            case  R.id.btn_del:
                if (!TextUtils.isEmpty(str)&str.length() > 0){
                    et_number.setText(str.substring(0,str.length()-1));
                    et_number.setSelection(str.length()-1);

                }
                break;
            case  R.id.btn_query:
                if (!TextUtils.isEmpty(str)){
                    getPhone(str);
                }
                break;
        }

    }

    public void getPhone(String str) {
        
      String  url = "http://apis.juhe.cn/mobile/get?phone="+str+"&key="+ StaticClass.PHONE_APPID;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                L.i(t);
                parseJson(t);
            }
        });
        
        
    }
    /**
     * "province":"浙江",
     * "city":"杭州",
     * "areacode":"0571",
     * "zip":"310000",
     * "company":"移动",
     * "card":"移动动感地带卡"
     */

    //解析Json
    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");
            String card = jsonResult.getString("card");

            tv_resule.setText("归属地:" + province + city + "\n"
                    + "区号:" + areacode + "\n"
                    + "邮编:" + zip + "\n"
                    + "运营商:" + company + "\n"
                    + "类型:" + card);


            switch (company){
                    case "移动":
                        iv_company.setBackgroundResource(R.drawable.china_mobile);
                        break;
                    case "联通":
                        iv_company.setBackgroundResource(R.drawable.china_unicom);
                        break;
                    case "电信":
                        iv_company.setBackgroundResource(R.drawable.china_telecom);
                        break;
                }
            flag = true;

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
