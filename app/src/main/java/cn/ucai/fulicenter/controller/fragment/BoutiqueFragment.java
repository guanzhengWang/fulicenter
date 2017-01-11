package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.net.IModelBoutique;
import cn.ucai.fulicenter.model.net.ModelBoutique;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {


    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.btRv)
    RecyclerView btRv;
    @BindView(R.id.btSrl)
    SwipeRefreshLayout btSrl;
    GridLayoutManager gm;
    IModelBoutique modelBoutique;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;

    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, view);
        initView();
        modelBoutique=new ModelBoutique();
        initData();
        return view;
    }

    private void initData() {
        modelBoutique.downData(getContext(),  new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                mAdapter.initData(list);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        btSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        btRv.setLayoutManager(gm);
        btRv.setHasFixedSize(true);
        mAdapter = new BoutiqueAdapter(getContext(), mList);
        btRv.setAdapter(mAdapter);
        btRv.addItemDecoration(new SpaceItemDecoration(30));

    }

}
