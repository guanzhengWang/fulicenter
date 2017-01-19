package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.GoodsDetailBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelGoodsDetail;
import cn.ucai.fulicenter.model.net.ModelGoodsDetail;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.MFGT;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

public class GoodsDetailActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivGoodShare)
    ImageView ivGoodShare;
    @BindView(R.id.ivGoodCollect)
    ImageView ivGoodCollect;
    @BindView(R.id.ivGoodCart)
    ImageView ivGoodCart;
    @BindView(R.id.tvEN)
    TextView tvEN;
    @BindView(R.id.tvNa)
    TextView tvNa;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvPriceCurrent)
    TextView tvPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;
    @BindView(R.id.layout_image)
    LinearLayout layoutImage;
    @BindView(R.id.wv_good_brief)
    WebView wvGoodBrief;
    @BindView(R.id.activity_goods_detail)
    LinearLayout activityGoodsDetail;
    IModelGoodsDetail model;
    int goodsId;
    boolean isCollect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        Intent intent = getIntent();
        goodsId = intent.getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        initData(goodsId);
        ButterKnife.bind(this);
    }

    private void initData(int goodsId) {
        model = new ModelGoodsDetail();
        model.downData(this, goodsId, new OnCompleteListener<GoodsDetailBean>() {
            @Override
            public void onSuccess(GoodsDetailBean result) {
                if (result != null) {
                    showGoodsDetail(result);
                } else {
                    MFGT.finish(GoodsDetailActivity.this);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showGoodsDetail(GoodsDetailBean result) {
        tvNa.setText(result.getGoodsName());
        tvEN.setText(result.getGoodsEnglishName());
        tvPrice.setText(result.getPromotePrice());
        tvPriceCurrent.setText(result.getCurrencyPrice());
        wvGoodBrief.loadDataWithBaseURL(null, result.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
        salv.startPlayLoop(indicator, getAlbumImagUrl(result), getAlbumImagCount(result));
    }

    private int getAlbumImagCount(GoodsDetailBean result) {
        if (result.getProperties() != null && result.getProperties().size() > 0) {
            return result.getProperties().get(0).getAlbums().size();
        }
        return 0;
    }


    private String[] getAlbumImagUrl(GoodsDetailBean result) {
        if (result.getProperties() != null && result.getProperties().size() > 0) {
            List<GoodsDetailBean.PropertiesBean.AlbumsBean> albums = result.getProperties().get(0).getAlbums();
            String[] urls = new String[albums.size()];
            for (int i = 0; i < albums.size(); i++) {
                urls[i] = albums.get(i).getImgUrl();
            }
            return urls;
        }
        return new String[0];
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCollectStatus();
    }
    @OnClick(R.id.ivGoodCollect)
    public void setCollectListener(){
           User user=FuLiCenterApplication.getUser();
        if(user!=null){
            setCollect(user);
        }else{
            MFGT.gotoLogin(this);
        }
    }

    private void setCollect(User user) {
        model.setCollect(this, goodsId, user.getMuserName(),
                isCollect ? I.ACTION_DELETE_COLLECT : I.ACTION_ADD_COLLECT, new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if(result!=null&&result.isSuccess()){
                            isCollect=!isCollect;
                            setCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                            sendBroadcast(new Intent(I.BROADCAST_UPDATA_COLLECT).putExtra(I.Collect.GOODS_ID,goodsId));
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void setCollectStatus() {
        if(isCollect){
            ivGoodCollect.setImageResource(R.mipmap.bg_collect_out);
        }else{
            ivGoodCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    private void initCollectStatus() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            model.isCollect(this, goodsId, user.getMuserName(), new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    Log.e("cccc","result"+result);
                    if (result != null && result.isSuccess()) {
                        isCollect = true;
                    } else {
                        isCollect = false;
                    }
                    setCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollect = false;
                    setCollectStatus();
                }
            });
        }
    }
}
