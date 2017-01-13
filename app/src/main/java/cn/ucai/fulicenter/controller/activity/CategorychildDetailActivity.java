package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.model.net.ModelNewGoods;

public class CategorychildDetailActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.alertTitle)
    RelativeLayout alertTitle;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.activity_categorychild_detail)
    LinearLayout activityCategorychildDetail;
    NewGoodsFragment mNewGoodsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorychild_detail);
        ButterKnife.bind(this);
        mNewGoodsFragment=new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .commit();
    }

    public void Back(View view) {
        this.finish();
    }

    @OnClick({R.id.sort_addtime, R.id.sort_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sort_addtime:
                mNewGoodsFragment.sortGoods(I.SORT_BY_ADDTIME_ASC);
                break;
            case R.id.sort_price:
                mNewGoodsFragment.sortGoods(I.SORT_BY_PRICE_ASC);
                break;
        }
    }
}
