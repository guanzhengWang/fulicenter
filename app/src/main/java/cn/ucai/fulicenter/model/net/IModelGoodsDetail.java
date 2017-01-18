package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.os.Message;
import android.view.View;

import cn.ucai.fulicenter.model.bean.GoodsDetailBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public interface IModelGoodsDetail {
    void downData(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailBean> listener);
    void isCollect(Context context, int goodsId, String username, OkHttpUtils.OnCompleteListener<MessageBean> listener);
    void setCollect(Context context, int goodsId, String username,int action, OkHttpUtils.OnCompleteListener<MessageBean> listener);
}
