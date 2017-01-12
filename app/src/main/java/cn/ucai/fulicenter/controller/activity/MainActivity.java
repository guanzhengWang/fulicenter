package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;

public class MainActivity extends AppCompatActivity {
    RadioButton[] rbs;
    int index, Currentindex;
    @BindView(R.id.layoutNewGood)
    RadioButton layoutNewGood;
    @BindView(R.id.layoutBoutique)
    RadioButton layoutBoutique;
    @BindView(R.id.layoutCategory)
    RadioButton layoutCategory;
    @BindView(R.id.layoutCart)
    RadioButton layoutCart;
    @BindView(R.id.layoutPersonal)
    RadioButton layoutPersonal;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;

    Fragment[] mFragments=new Fragment[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mNewGoodsFragment=new NewGoodsFragment();
        mBoutiqueFragment=new BoutiqueFragment();
        rbs = new RadioButton[5];

        rbs[0] = layoutNewGood;
        rbs[1] = layoutBoutique;
        rbs[2] = layoutCategory;
        rbs[3] = layoutCart;
        rbs[4] = layoutPersonal;
        mFragments[0]=mNewGoodsFragment;
        mFragments[1]=mBoutiqueFragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,mNewGoodsFragment)
                .add(R.id.fragment_container,mBoutiqueFragment)
                .show(mNewGoodsFragment)
                .hide(mBoutiqueFragment)
                .commit();
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layoutNewGood:
                index = 0;
                break;
            case R.id.layoutBoutique:
                index = 1;
                break;
            case R.id.layoutCategory:
                index = 2;
                break;
            case R.id.layoutCart:
                index = 3;
                break;
            case R.id.layoutPersonal:
                index = 4;
                break;
        }
        setFragment();
        if (index != Currentindex) {
            setStatus();
        }
    }

    private void setFragment() {
            getSupportFragmentManager().beginTransaction()
                    .show(mFragments[index])
                    .hide(mFragments[Currentindex])
                    .commit();
        Log.e("MainActiivty",index + "");
        Log.e("MainActiivty",Currentindex + "");
    }

    public void setStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        Currentindex = index;
    }
}
