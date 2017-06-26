package com.example.pojo.smartapp.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pojo.smartapp.R;
import com.example.pojo.smartapp.entity.User;
import com.example.pojo.smartapp.ui.DeliveryActivity;
import com.example.pojo.smartapp.ui.LoginActivity;
import com.example.pojo.smartapp.ui.PhoneActivity;
import com.example.pojo.smartapp.utils.L;
import com.example.pojo.smartapp.utils.UtilTools;
import com.example.pojo.smartapp.view.CustomDialog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 项目名: SmartApp
 * 包名: com.example.pojo.smartapp.fragment
 * 创建者:  zsj
 * 创建事件: 2017/4/10 16:30
 * 描述:  用户界面
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_edit;
    private EditText et_username;
    private EditText et_gender;
    private EditText et_age;
    private EditText et_desc;
    private Button   btn_update_ok;
    private Button   btn_exit;

    private CircleImageView profile_image;
    private CustomDialog dialog;

    private  Button btn_camera;
    private  Button btn_picture;
    private  Button btn_cancel;
    private TextView tv_delivery;
    private TextView tv_phone;
    
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment,null);
        initView(view);
        return view;
    }
    //初始化
    private void initView(View view) {
        et_username = (EditText) view.findViewById(R.id.et_username);
        et_gender = (EditText) view.findViewById(R.id.et_gender);
        et_age = (EditText) view.findViewById(R.id.et_age);
        et_desc = (EditText) view.findViewById(R.id.et_desc);

        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_update_ok = (Button) view.findViewById(R.id.btn_update_ok);
        btn_exit = (Button) view.findViewById(R.id.btn_exit);

        tv_delivery = (TextView) view.findViewById(R.id.tv_delivery);
        tv_delivery.setOnClickListener(this);
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(this);
        //圆形头像初始化
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);
        //对话框
        dialog = new CustomDialog(getActivity(),0,0,R.layout.dialog_phone,R.style.pop_anim_style, Gravity.BOTTOM,0);
        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_cancel = (Button)dialog.findViewById(R.id.btn_cancel);

        //从SharePreferences 获取头像
        UtilTools.getImageFromSharePre(getActivity(),profile_image);

        //设置EditText不可获取焦点
        setEnable(false);

        btn_edit.setOnClickListener(this);
        btn_update_ok.setOnClickListener(this);
        btn_exit.setOnClickListener(this);

        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    public void setEnable(boolean isOpen){
        et_username.setEnabled(isOpen);
        et_gender.setEnabled(isOpen);
        et_age.setEnabled(isOpen);
        et_desc.setEnabled(isOpen);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            //编辑
            case R.id.btn_edit:
                setEnable(true);
                btn_update_ok.setVisibility(View.VISIBLE);
                break;
            //更新用户
            case R.id.btn_update_ok:
                //获取用户信息
                String name = et_username.getText().toString().trim();
                String gender  = et_gender.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();
                //判断是否输入为空
                if (!TextUtils.isEmpty(name)&!TextUtils.isEmpty(gender)
                        &!TextUtils.isEmpty(age)){
                    //更新用户
                    User user = new User();
                    user.setUsername(name);
                    //性别
                    user.setAge(Integer.parseInt(age));
                    if (gender.equals("男")||gender.equals("nan"))
                            {
                        user.setSex(true);
                    }else {
                        user.setSex(false);
                    }
                // 简介
                if (TextUtils.isEmpty(desc)){
                    user.setDesc("这个人很懒，什么都没有留下...");
                }else {
                    user.setDesc(desc);
                }
                //更新
                   BmobUser bmobUser = BmobUser.getCurrentUser();
                   user.update(bmobUser.getObjectId(),new UpdateListener() {
                       @Override
                       public void done(BmobException e) {
                           if (e == null){
                               setEnable(false);
                               btn_update_ok.setVisibility(View.GONE);
                               Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
                           }else {
                               Toast.makeText(getActivity(), "更新失败", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });

                }else {
                    Toast.makeText(getActivity(), "输入不能为空！", Toast.LENGTH_SHORT).show();
                }

                break;
            //退出登录
            case R.id.btn_exit:
                User.logOut();
                // 现在的currentUser是null了
                BmobUser currentUser = User.getCurrentUser();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            //修改头像
            case  R.id.profile_image:
                dialog.show();
                break;
            //启动相机
            case R.id.btn_camera:
                toCamera();
                break;
            //打开相册
            case R.id.btn_picture:
                toPicture();
                break;
            //取消
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            //物流查询
            case R.id.tv_delivery:
                startActivity(new Intent(getActivity(),DeliveryActivity.class));
                break;
            //电话归属地查询
            case  R.id.tv_phone:
                startActivity(new Intent(getActivity(),PhoneActivity.class));
                break;
        }
    }

    public static  final  String  PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    //跳转相册
    private void toPicture() {
         Intent intent = new Intent(Intent.ACTION_PICK);
         intent.setType("image/*");
         startActivityForResult(intent,IMAGE_REQUEST_CODE);
         dialog.dismiss();

    }
    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);

                    break;
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        //设置图片
                        setImageToView(data);
                        //将原先图片删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //裁剪图片
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }
    //设置图片
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle!= null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存头像
        UtilTools.putImageToSharePre(getActivity(),profile_image);
    }
}
