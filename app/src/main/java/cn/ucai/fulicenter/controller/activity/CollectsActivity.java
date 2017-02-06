package cn.ucai.fulicenter.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.CollectsAdapter;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;
import cn.ucai.fulicenter.view.DisplayUtils;

import static cn.ucai.fulicenter.application.I.ACTION_DOWNLOAD;
import static cn.ucai.fulicenter.application.I.ACTION_PULL_DOWN;
import static cn.ucai.fulicenter.application.I.ACTION_PULL_UP;

public class CollectsActivity extends AppCompatActivity {

    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    IModelUser model;
    User user;
    int pageId=1;
    GridLayoutManager gm;
    CollectsAdapter mAdapter;
    ArrayList<CollectBean> mList;
    receiver mreceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collects);
        ButterKnife.bind(this);
        DisplayUtils.initBackTitle(this, "收藏的商品");
        user= FuLiCenterApplication.getUser();
        if(user==null){

        }else {
            initData(ACTION_DOWNLOAD);
            setListener();
            initView();
            setReceiver();
        }
    }

    private void setReceiver() {
        mreceiver=new receiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(I.BROADCAST_UPDATE_COLLECT);
        registerReceiver(mreceiver,intentFilter);
    }

    private void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        gm = new GridLayoutManager(this, I.COLUM_NUM);
        mList=new ArrayList<>();
        rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);
        mAdapter = new CollectsAdapter(this,mList);
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(30));
    }

    private void initData(final int action) {
        model=new ModelUser();
        model.getCollects(this, user.getMuserName(), pageId, I.PAGE_SIZE_DEFAULT, new OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {

                ArrayList<CollectBean> list = ConvertUtils.array2List(result);
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
        });
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
                pageId = 1;
                initData(ACTION_PULL_DOWN);
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
                    pageId++;
                    initData(ACTION_PULL_UP);
                }
            }
        });
    }
    class receiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra(I.Collect.GOODS_ID, 0);
            mAdapter.removeItem(id);
        }
    }

}
