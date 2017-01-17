package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.net.IModelNewGoods;
import cn.ucai.fulicenter.model.net.ModelNewGoods;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    private final int ACTION_DOWNLOAD = 0;
    private final int ACTION_PULL_DOWN = 1;
    private final int ACTION_PULL_UP = 2;

    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    GridLayoutManager gm;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList = new ArrayList<>();
    IModelNewGoods model;
    int mPageId = 1;


    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        initView();
        model = new ModelNewGoods();
        initData(ACTION_DOWNLOAD, mPageId);
        setListener();
        return layout;
    }

    private void setListener() {
        setPullDownListener();

        setPullUpListener();
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                mPageId = 1;
                initData(ACTION_PULL_DOWN, mPageId);
            }
        });
    }

    private void setPullUpListener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mAdapter.setDragging(newState == rv.SCROLL_STATE_DRAGGING);
                int position = gm.findLastVisibleItemPosition();
                if (mAdapter.isMore() && position == mAdapter.getItemCount() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mPageId++;
                    initData(ACTION_PULL_UP, mPageId);
                }
            }
        });
    }

    private void initData(final int action, int mPageId) {
        int catId=getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID,I.CAT_ID);
        model.downData(getContext(), catId, mPageId, new OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                        ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                        mAdapter.setMore(result != null && result.length > 0);
                        if (!mAdapter.isMore()) {
                            if (action == ACTION_PULL_UP) {
                                mAdapter.setFooter("没有更多数据");
                            }
                            return;
                        }
                        mAdapter.setFooter("加载更多数据");
                        switch (action)

                        {
                            case ACTION_DOWNLOAD:
                                mAdapter.initData(list);
                                break;
                            case ACTION_PULL_DOWN:
                                mAdapter.initData(list);
                                srl.setRefreshing(false);
                                tvRefresh.setVisibility(View.GONE);
                            case ACTION_PULL_UP:
                                mAdapter.addData(list);
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                }

        );
    }


    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);
        mAdapter = new GoodsAdapter(getContext(), mList);
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(30));

    }
    public void sortGoods(int sortBy){
        mAdapter.SorGoods(sortBy);
    }

}
