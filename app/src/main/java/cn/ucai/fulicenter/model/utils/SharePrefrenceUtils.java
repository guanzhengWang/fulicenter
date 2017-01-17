package cn.ucai.fulicenter.model.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/17 0017.
 */

public class SharePrefrenceUtils {
    private static final String SHARE_PREFRENCE_NAME ="cn.ucai.fulicenter_user";
    private static final String SHARE_PREFRENCE_NAME_USERNAME ="cn.ucai.fulicenter_user_username";
    private static SharePrefrenceUtils instance;
    private static SharedPreferences msharedpreferences;
    private static SharedPreferences.Editor mEditor;
    public static final String SHARE_KEY_USER_NAME ="share_key_user_name";
    public SharePrefrenceUtils(Context context){
        msharedpreferences=context.getSharedPreferences(SHARE_PREFRENCE_NAME,Context.MODE_PRIVATE);
        mEditor=msharedpreferences.edit();
    }
    public static SharePrefrenceUtils getInstance(Context context){
        if(instance==null){
            instance=new SharePrefrenceUtils(context);
        }
        return instance;
    }
    public  void saveUser(String username){
        mEditor.putString(SHARE_PREFRENCE_NAME_USERNAME,username).commit();
    }
    public  String getUser(){
        return msharedpreferences.getString(SHARE_PREFRENCE_NAME_USERNAME,null);
    }
    public void removeUser(){
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }
}
