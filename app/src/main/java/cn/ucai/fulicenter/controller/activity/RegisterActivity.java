package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ResultUtils;
import cn.ucai.fulicenter.view.MFGT;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etNick)
    EditText etNick;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirm)
    EditText etConfirm;
    IModelUser model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.btRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btRegister:
                checkInput();
                break;
        }
    }

    private void checkInput() {
        String Username=etUserName.getText().toString().trim();
        String Nick=etNick.getText().toString().trim();
        String Password=etPassword.getText().toString().trim();
        String Confirm=etConfirm.getText().toString().trim();
        if(TextUtils.isEmpty(Username)){
            etUserName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
        }else if(!Username.matches("[a-zA-z]\\w{5,15}")){
            etUserName.setError(getResources().getString(R.string.illegal_user_name));
            etUserName.requestFocus();
        }
        else if(TextUtils.isEmpty(Nick)){
            etUserName.setError(getResources().getString(R.string.nick_name_connot_be_empty));
            etUserName.requestFocus();
        }else if(TextUtils.isEmpty(Password)){
            etUserName.setError(getResources().getString(R.string.password_connot_be_empty));
            etUserName.requestFocus();
        }else if(TextUtils.isEmpty(Confirm)){
            etUserName.setError(getResources().getString(R.string.confirm_password_connot_be_empty));
            etUserName.requestFocus();
        }else {
            register(Username,Nick,Password);
        }
    }

    private void register(String username, String nick, String password) {
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage(getString(R.string.registering));
        model=new ModelUser();
        model.Register(this, username, nick, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String str) {
                if(str!=null){
                    Result result =ResultUtils.getResultFromJson(str,User.class);
                    if(result !=null){
                        if(result.isRetMsg()){
                            CommonUtils.showLongToast(R.string.register_success);
                            MFGT.finish(RegisterActivity.this);
                        }else{
                            CommonUtils.showLongToast(R.string.register_fail_exists);
                        }
                    }else{
                        CommonUtils.showLongToast(R.string.register_fail);
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                dialog.dismiss();
                CommonUtils.showLongToast(error);
            }
        });
    }
}
