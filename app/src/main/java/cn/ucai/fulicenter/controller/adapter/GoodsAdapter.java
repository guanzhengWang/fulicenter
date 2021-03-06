package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<NewGoodsBean> mList;
    boolean isMore;

    boolean isDragging;
    String Footer;

    public String getFooter() {
        return Footer;
    }
    public void SorGoods(final int sorBy){
        Collections.sort(mList, new Comparator<NewGoodsBean>() {
            @Override
            public int compare(NewGoodsBean leftBean, NewGoodsBean rightBean) {
                int result=0;
                switch (sorBy){
                    case I.SORT_BY_ADDTIME_ASC:
                        result= (int) (leftBean.getAddTime()-rightBean.getAddTime());
                        break;
                    case I.SORT_BY_ADDTIME_DESC:
                        result= (int) (rightBean.getAddTime()-leftBean.getAddTime());
                        break;
                    case I.SORT_BY_PRICE_ASC:
                        result=getPrice(leftBean.getCurrencyPrice())-getPrice(rightBean.getCurrencyPrice());
                        break;
                    case I.SORT_BY_PRICE_DESC:
                        result=getPrice(rightBean.getCurrencyPrice())-getPrice(leftBean.getCurrencyPrice());
                        break;
                }
                return result;
            }
        });
        notifyDataSetChanged();
    }
    int getPrice(String price){
        int p=0;
        p= Integer.parseInt(price.substring(price.indexOf("￥")+1));
        Log.e("adapter","p"+p);
        return p;
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

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public static class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public GoodsAdapter(Context mContext, ArrayList<NewGoodsBean> List) {
        this.mContext = mContext;
        this.mList = List;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case I.TYPE_ITEM:
                RecyclerView.ViewHolder holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
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
        GoodsViewHolder gvh = (GoodsViewHolder) holder;
        ImageLoader.downloadImg(mContext, gvh.ivGoodsThumb, mList.get(position).getGoodsThumb());
        gvh.tvGoodsName.setText(mList.get(position).getGoodsName().toString());
        gvh.tvGoodsPrice.setText(mList.get(position).getCurrencyPrice().toString());
        gvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFGT.gotoGoodsDetail(mContext,mList.get(position).getGoodsId());
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
        return mList.size()+1;
    }

}
