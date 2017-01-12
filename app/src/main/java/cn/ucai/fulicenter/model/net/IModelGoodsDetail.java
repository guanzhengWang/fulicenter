package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.view.View;

import cn.ucai.fulicenter.model.bean.GoodsDetailBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public interface IModelGoodsDetail {
    void downData(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailBean> listener);
}
