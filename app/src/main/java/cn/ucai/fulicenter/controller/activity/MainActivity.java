package cn.ucai.fulicenter.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import cn.ucai.fulicenter.R;

public class MainActivity extends AppCompatActivity {
    RadioButton rbNewGoods,rbBoutique,rbCategory,rbCart,rbPersonal;
    RadioButton [] rbs;
    int index,currentindex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        rbs=new RadioButton[5];
        rbNewGoods= (RadioButton) findViewById(R.id.NewGood);
        rbBoutique = (RadioButton) findViewById(R.id.Boutique);
        rbCategory = (RadioButton) findViewById(R.id.Category);
        rbCart = (RadioButton) findViewById(R.id.Cart);
        rbPersonal= (RadioButton) findViewById(R.id.Personal);
        rbs[0]=rbNewGoods;
        rbs[1]=rbBoutique;
        rbs[2]=rbCategory;
        rbs[3]=rbCart;
        rbs[4]=rbPersonal;
    }
    public void onCheckedChange(View view){
        switch (view.getId()){
            case R.id.NewGood:
                index=0;
                break;
            case R.id.Boutique:
                index=1;
                break;
            case R.id.Category:
                index=2;
                break;
            case R.id.Cart:
                index=3;
                break;
            case R.id.Personal:
                index=4;
                break;
        }
        if(index!=currentindex){
            setStatus();
        }
    }
    public void setStatus(){
        for(int i=0;i<rbs.length;i++){
            if(index!=i){
                rbs[i].setChecked(false);
            }else {
                rbs[i].setChecked(true);
            }
        }
        currentindex=index;
    }
}
