package cn.ucai.fulicenter.controller.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;

public class CategorychildDetailActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.alertTitle)
    RelativeLayout alertTitle;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.activity_categorychild_detail)
    LinearLayout activityCategorychildDetail;
    NewGoodsFragment mNewGoodsFragment;
    boolean priceAsc = false;
    boolean addtimeAsc = false;
    @BindView(R.id.sort_addtime)
    Button sortAddtime;
    @BindView(R.id.sort_price)
    Button sortPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorychild_detail);
        ButterKnife.bind(this);
        mNewGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .commit();
    }

    public void Back(View view) {
        this.finish();
    }

    @OnClick({R.id.sort_addtime, R.id.sort_price})
    public void onClick(View view) {
        int sortBy = I.SORT_BY_ADDTIME_ASC;
        Drawable up = getResources().getDrawable(R.drawable.arrow_order_up);
        Drawable down = getResources().getDrawable(R.drawable.arrow_order_down);
        switch (view.getId()) {
            case R.id.sort_addtime:
                if (addtimeAsc) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                    up.setBounds(0,0,up.getIntrinsicWidth(),up.getIntrinsicHeight());
                    sortAddtime.setCompoundDrawables(null,null,up,null);
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                    down.setBounds(0,0,up.getIntrinsicWidth(),up.getIntrinsicHeight());
                    sortAddtime.setCompoundDrawables(null,null,down,null);
                }
                addtimeAsc = !addtimeAsc;
                break;
            case R.id.sort_price:
                if (priceAsc) {
                    sortBy = I.SORT_BY_PRICE_ASC;
                    up.setBounds(0,0,up.getIntrinsicWidth(),up.getIntrinsicHeight());
                    sortPrice.setCompoundDrawables(null,null,up,null);
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;
                    down.setBounds(0,0,up.getIntrinsicWidth(),up.getIntrinsicHeight());
                    sortPrice.setCompoundDrawables(null,null,down,null);
                }
                priceAsc = !priceAsc;
                break;
        }
        mNewGoodsFragment.sortGoods(sortBy);
    }
}
