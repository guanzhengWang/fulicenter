package cn.ucai.fulicenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.controller.activity.CategorychildDetailActivity;
import cn.ucai.fulicenter.controller.activity.CollectsActivity;
import cn.ucai.fulicenter.controller.activity.GoodsDetailActivity;
import cn.ucai.fulicenter.controller.activity.LoginActivity;
import cn.ucai.fulicenter.controller.activity.OrderActivity;
import cn.ucai.fulicenter.controller.activity.RegisterActivity;
import cn.ucai.fulicenter.controller.activity.SettingActivity;
import cn.ucai.fulicenter.controller.activity.UpdateNickActivity;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailBean;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void startActivity(Activity context, Class<?> cla){
        context.startActivity(new Intent(context,cla));
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void startActivity(Activity context, Intent intent){
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoBoutiqueChild(Context context, BoutiqueBean boutiqueBean){
        Intent intent=new Intent(context, BoutiqueChildActivity.class);
                intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID,boutiqueBean.getId());
                intent.putExtra(I.Boutique.NAME,boutiqueBean.getName());
                startActivity((Activity) context,intent);
    }
    public static void gotoGoodsDetail(Context context, int goodsId){
        Intent intent=new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(I.Goods.KEY_GOODS_ID,goodsId);
        startActivity((Activity) context,intent);
    }

    public static void gotoCategoryChildDetail(Context mContext, int categoryChildBean, String groupName, ArrayList<CategoryChildBean> list) {
        Intent intent =new Intent(mContext, CategorychildDetailActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID,categoryChildBean);
        intent.putExtra(I.CategoryGroup.NAME,groupName);
        intent.putExtra(I.CategoryChild.DATA,list);
        startActivity((Activity)mContext,intent);
    }

    public static void gotoLogin(Activity context) {
        context.startActivityForResult(new Intent(context,LoginActivity.class),I.REQUEST_CODE_LOGIN);
    }

    public static void gotoRegister(LoginActivity loginActivity) {
        startActivity(loginActivity,RegisterActivity.class);
    }

    public static void gotSetting(Activity activity) {
        startActivity(activity, SettingActivity.class);
    }

    public static void gotoUpdateNick(Activity activity) {
        activity.startActivityForResult(new Intent(activity,UpdateNickActivity.class),I.REQUEST_CODE_NICK);
    }

    public static void gotoCollectsActivity(Activity activity) {
        startActivity(activity, CollectsActivity.class);
    }

    public static void gotoOrder(Activity activity, int priceSum) {
        Intent intent=new Intent(activity,OrderActivity.class);
        intent.putExtra(I.Cart.PAY_PRICE,priceSum);
        startActivity(activity, intent);
    }
}
