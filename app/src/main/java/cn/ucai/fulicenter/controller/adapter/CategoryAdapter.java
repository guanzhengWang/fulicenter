package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.MFGT;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mgroupList;
    ArrayList<ArrayList<CategoryChildBean>> mchildList;


    public CategoryAdapter(ArrayList<ArrayList<CategoryChildBean>> childList, ArrayList<CategoryGroupBean> groupList, Context mContext) {
        this.mContext = mContext;
        this.mchildList = childList;
        this.mgroupList = groupList;
        mchildList = new ArrayList<>();
        mchildList.addAll(childList);
        mgroupList = new ArrayList<>();
        mgroupList.addAll(groupList);
    }

    @Override
    public int getGroupCount() {
        return mgroupList!=null?mgroupList.size():0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mchildList!=null&&mchildList.get(groupPosition)!=null?mchildList.get(groupPosition).size():0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mgroupList.get(groupPosition);
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        if(mchildList!=null&&mchildList.get(groupPosition)!=null){
            return mchildList.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpand, View view, ViewGroup viewGroup) {
        GroupViewHolder vh = null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            vh = new GroupViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (GroupViewHolder) view.getTag();
        }
        ImageLoader.downloadImg(mContext, vh.ivGroupThumb, mgroupList.get(groupPosition).getImageUrl());
        vh.tvGroupName.setText(mgroupList.get(groupPosition).getName());
        vh.ivIndicator.setImageResource(isExpand ? R.mipmap.expand_off : R.mipmap.expand_on);
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isExpand, View view, ViewGroup viewGroup) {
        ChildViewHolder vh=null;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_child, null);
            vh=new ChildViewHolder(view);
            view.setTag(vh);
        }else{
            vh= (ChildViewHolder) view.getTag();
        }
        ImageLoader.downloadImg(mContext,vh.ivCategoryChildThumb,getChild(groupPosition,childPosition).getImageUrl());
        vh.tvCategoryChildName.setText(mchildList.get(groupPosition).get(childPosition).getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFGT.gotoCategoryChildDetail(mContext,
                        mchildList.get(groupPosition).get(childPosition).getId(),
                        mgroupList.get(groupPosition).getName(),
                        mchildList.get(groupPosition));
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupList, ArrayList<ArrayList<CategoryChildBean>> childList) {
        mgroupList.clear();
        mgroupList.addAll(groupList);
        mchildList.clear();
        mchildList.addAll(childList);
        notifyDataSetChanged();
    }

    static class GroupViewHolder {
        @BindView(R.id.ivGroupThumb)
        ImageView ivGroupThumb;
        @BindView(R.id.tvGroupName)
        TextView tvGroupName;
        @BindView(R.id.ivIndicator)
        ImageView ivIndicator;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.ivCategoryChildThumb)
        ImageView ivCategoryChildThumb;
        @BindView(R.id.tvCategoryChildName)
        TextView tvCategoryChildName;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
