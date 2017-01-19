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
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.net.IModelGoodsDetail;
import cn.ucai.fulicenter.model.net.ModelGoodsDetail;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
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
    IModelGoodsDetail model;

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
        mList = new ArrayList<>();
        this.mList = List;
        model=new ModelGoodsDetail();

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

    public void removeItem(int id) {
        if(id!=0){
            mList.remove(new CollectBean(id));
            notifyDataSetChanged();
        }
    }


    class CollectsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        int itemPosition;
        CollectsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            itemPosition=position;
            ImageLoader.downloadImg(mContext, ivGoodsThumb, mList.get(position).getGoodsThumb());
            tvGoodsName.setText(mList.get(position).getGoodsName().toString());
        }
        @OnClick({R.id.delete_collect, R.id.rl_collect})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.delete_collect:
                    model.setCollect(mContext, mList.get(itemPosition).getGoodsId(), FuLiCenterApplication.getUser().getMuserName(),
                            I.ACTION_DELETE_COLLECT, new OnCompleteListener<MessageBean>() {
                                @Override
                                public void onSuccess(MessageBean result) {
                                    if(result!=null&&result.isSuccess()){
                                        mList.remove(itemPosition);
                                        notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onError(String error) {

                                }
                            });
                    break;
                case R.id.rl_collect:
                    MFGT.gotoGoodsDetail(mContext,mList.get(itemPosition).getGoodsId());
                    break;
            }
        }
    }
}
