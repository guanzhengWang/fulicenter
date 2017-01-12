package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.GoodsDetailBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public class ModelGoodsDetail implements IModelGoodsDetail{
    @Override
    public void downData(Context context, int goodsId, OkHttpUtils.OnCompleteListener<GoodsDetailBean> listener) {
        OkHttpUtils <GoodsDetailBean> utils=new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOOD_DETAILS)
                .addParam(I.Goods.KEY_GOODS_ID, String.valueOf(goodsId))
                .targetClass(GoodsDetailBean.class)
                .execute(listener);
    }
}
