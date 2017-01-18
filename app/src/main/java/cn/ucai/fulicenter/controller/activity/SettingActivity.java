package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.model.utils.SharePrefrenceUtils;
import cn.ucai.fulicenter.view.DisplayUtils;
import cn.ucai.fulicenter.view.MFGT;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_nick)
    TextView tvUserNick;
    OnSetAvatarListener mOnSetAvatarListener;
    IModelUser model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        DisplayUtils.initBackTitle(this,"设置");
        initData();
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            MFGT.gotoLogin(this);
        }
    }

    private void loadUserInfo(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),this, ivUserAvatar);
        tvUserName.setText(user.getMuserName());
        tvUserNick.setText(user.getMuserNick());
    }

    @OnClick(R.id.btn_logout)
    public void logout() {
        FuLiCenterApplication.setUser(null);
        SharePrefrenceUtils.getInstance(this).removeUser();
        MFGT.gotoLogin(this);
        finish();
    }
    @OnClick(R.id.rl_nick)
    public void updateNick(){
        MFGT.gotoUpdateNick(this);
    }
    @OnClick(R.id.rl_name)
    public void onClickUserName(){
        CommonUtils.showLongToast(R.string.username_connot_be_modify);
    }
    @OnClick(R.id.rl_avatar)
    public void onClickAvatar(){
        mOnSetAvatarListener=new OnSetAvatarListener(this,R.id.rl_avatar,
                FuLiCenterApplication.getUser().getMuserName(),
                I.AVATAR_TYPE_USER_PATH);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }if(requestCode== I.REQUEST_CODE_NICK){
            tvUserNick.setText(FuLiCenterApplication.getUser().getMuserNick());
        }else if(requestCode==OnSetAvatarListener.REQUEST_CROP_PHOTO){
                uploadAvatar();
        }else{
            mOnSetAvatarListener.setAvatar(requestCode,data,ivUserAvatar);
        }
    }

    private void uploadAvatar() {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage(getString(R.string.update_user_avatar));
        dialog.show();
        User user=FuLiCenterApplication.getUser();
        model=new ModelUser();
        File file=null;
        file=new File(String.valueOf(OnSetAvatarListener.
                getAvatarFile(this,I.AVATAR_TYPE_USER_PATH+"/"+user.getMuserName()+user.getMavatarSuffix())));
        model.UploadAvatar(this,
               user.getMuserName(), file,
                new OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.e("uploadAvatar","s"+s);
                        int msg=R.string.update_user_avatar_fail;
                        if(s!=null){
                            Result result = ResultUtils.getResultFromJson(s, User.class);
                            if(result!=null){
                                if(result.isRetMsg()){
                                    msg=R.string.update_user_avatar_success;
                                }
                            }
                        }
                        CommonUtils.showLongToast(msg);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(String error) {
                        CommonUtils.showLongToast(error);
                        dialog.dismiss();
                    }
                });
    }
}
