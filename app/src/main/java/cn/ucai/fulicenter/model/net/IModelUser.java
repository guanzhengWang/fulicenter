package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.view.View;

import java.io.File;

import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModelUser {
    void Login(Context context,String username,String password, OnCompleteListener<String> listener);
    void Register(Context context,String username,String usernick,String password, OnCompleteListener<String> listener);
    void UpdateNick(Context context,String username,String usernick, OnCompleteListener<String> listener);
    void UploadAvatar(Context context,String username,File file,OnCompleteListener<String> listener);
    void CollectCount(Context context, String username, OnCompleteListener<MessageBean>listener);
    void getCollects(Context context, String username,int goodsId,int pageSize , OnCompleteListener<CollectBean[]> listener);
    void getCart(Context context, String username,OnCompleteListener<CartBean[]>listener);
    void addCart(Context context, String username,int goodsId,int count,OnCompleteListener<MessageBean>listener);
    void delCart(Context context, int cartId, OnCompleteListener<MessageBean> listener);
    void updateCart(Context context, int cartId, int count, OnCompleteListener<MessageBean> listener);
    void updateCart(Context context, int action, String username, int goodsId, int count ,int cartId,OnCompleteListener<MessageBean> listener);
}
