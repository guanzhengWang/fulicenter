package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class CollectsAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CollectBean> mList;
    boolean isMore;

    boolean isDragging;
    String Footer;

    public String getFooter() {
        return Footer;
    }

    public void setFooter(String footer) {
        Footer = footer;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }



    public CollectsAdapter(Context mContext, ArrayList<CollectBean> List) {
        this.mContext = mContext;
        mList=new ArrayList<>();
        this.mList = List;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case I.TYPE_ITEM:
                RecyclerView.ViewHolder holder = new CollectsViewHolder(View.inflate(mContext, R.layout.item_collects, null));
                return holder;
            case I.TYPE_FOOTER:
                RecyclerView.ViewHolder holder1 = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
                return holder1;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder fvh = (FooterViewHolder) holder;
            fvh.tvFooter.setText(getFooter());
            return;
        }
        CollectsViewHolder cvh = (CollectsViewHolder) holder;
        ImageLoader.downloadImg(mContext, cvh.ivGoodsThumb, mList.get(position).getGoodsThumb());
        cvh.tvGoodsName.setText(mList.get(position).getGoodsName().toString());
        cvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFGT.gotoGoodsDetail(mContext, mList.get(position).getGoodsId());
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

     static class CollectsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;

        CollectsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
