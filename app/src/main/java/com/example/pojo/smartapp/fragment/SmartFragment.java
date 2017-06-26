package com.example.pojo.smartapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.adapter.ChatListAdapter;
import com.example.pojo.smartapp.entity.ChatData;
import com.example.pojo.smartapp.utils.ShareUtils;
import com.example.pojo.smartapp.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.fragment
 * 创建者:  zsj
 * 创建事件: 2017/4/10 16:30
 * 描述:   机器人聊天
 */

public class SmartFragment extends Fragment implements View.OnClickListener {

    private ListView mListView;
    private EditText et_text;
    private Button btn_send;
    private ChatListAdapter adapter ;
    //TTS
    private SpeechSynthesizer mTts;

    private List<ChatData> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.smart_fragment,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");

        mListView = (ListView) view.findViewById(R.id.mListView);
        et_text = (EditText) view.findViewById(R.id.et_text);
        btn_send= (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        adapter = new ChatListAdapter(getActivity(),mList);
        mListView.setAdapter(adapter);
        addLeftItem("你好，我是小生");

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_send:
                //获取输入框数据
                String str = et_text.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(str)){
                    //判断长度不大于30
                    if (str.length()>30){
                        Toast.makeText(getActivity(), "输入的文字太长，不能识别", Toast.LENGTH_SHORT).show();
                    }else {
                        //清空输入框文字
                        et_text.setText("");
                        //添加文字到右边Item
                        addRightItem(str);
                        //发送给机器人请求返回内容
                        String url = "http://op.juhe.cn/robot/index?info="+str+"&key="+ StaticClass.SMART_APPID;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                super.onSuccess(t);
                                parseJson(t);
                            }
                        });
                    }

                }else {
                    Toast.makeText(getActivity(), "输入为空", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    //解析Json
    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String text = jsonResult.getString("text");
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //添加文字到右边
    private void addRightItem(String str) {
        ChatData chatData = new ChatData();
        chatData.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        chatData.setText(str);
        mList.add(chatData);
        //刷新listview
        adapter.notifyDataSetChanged();
        //滚动到底部
        mListView.setSelection(mListView.getBottom());
    }
    //添加文字到左边
    private void addLeftItem(String str) {
        boolean isSpeak = ShareUtils.getBoolean(getActivity(), "isSpeak", false);
        if (isSpeak) {
            startSpeak(str);
        }
        ChatData chatData = new ChatData();
        chatData.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        chatData.setText(str);
        mList.add(chatData);
        //刷新listview
        adapter.notifyDataSetChanged();
        //滚动到底部
        mListView.setSelection(mListView.getBottom());
    }
    //开始说话
    private void startSpeak(String text) {
        //3.开始合成
        mTts.startSpeaking(text, mSynListener);
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };


}
