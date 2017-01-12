package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    LinearLayoutManager LM;
    IModelBoutique modelBoutique;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;
    @BindView(R.id.tvLoad)
    TextView tvLoad;

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
        modelBoutique = new ModelBoutique();
        initData(I.ACTION_DOWNLOAD);
        setListener();
        return view;
    }

    private void setListener() {
        setPullDownListener();
    }

    private void setPullDownListener() {
        btSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                btSrl.setRefreshing(true);
                btSrl.setVisibility(View.VISIBLE);
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void initData(final int action) {
        modelBoutique.downData(getContext(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                btSrl.setRefreshing(false);
                btRv.setVisibility(View.VISIBLE);
                ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                if(action==I.ACTION_DOWNLOAD|action==I.ACTION_PULL_DOWN){

                    mAdapter.initData(list);
                }
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
        mList = new ArrayList<>();
        LM = new LinearLayoutManager(getContext());
        btRv.setLayoutManager(LM);
        btRv.setHasFixedSize(true);
        mAdapter = new BoutiqueAdapter(getContext(), mList);
        btRv.setAdapter(mAdapter);
        btRv.addItemDecoration(new SpaceItemDecoration(30));

    }

    @OnClick(R.id.tvLoad)
    public void onClick() {

        initData(I.ACTION_DOWNLOAD);
    }
}
