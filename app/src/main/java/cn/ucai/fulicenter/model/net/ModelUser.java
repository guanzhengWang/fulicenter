package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.MD5;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class ModelUser implements IModelUser{
    @Override
    public void Login(Context context, String username, String password, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void Register(Context context, String username, String usernick, String password, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,usernick)
                .addParam(I.User.PASSWORD,MD5.getMessageDigest(password))
                .post()
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void UpdateNick(Context context, String username, String usernick, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK, usernick)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void UploadAvatar(Context context, String username, File file, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID,username)
                .addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
                .addFile(file)
                .post()
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void getCollects(Context context, String username, int pageId, int pageSize, OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils =new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(pageSize))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }
}
