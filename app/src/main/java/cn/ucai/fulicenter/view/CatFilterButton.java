package cn.ucai.fulicenter.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.activity.CategorychildDetailActivity;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class CatFilterButton extends Button {
    boolean isExpand;
    PopupWindow mPopupWindow;
    CatFilterAdapter mAdapter;
    GridView mGridView;
    Context mContext;
    String GroupName;

    public CatFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void initCatFilterButton(String groupName, ArrayList<CategoryChildBean> list) {
        this.GroupName=groupName;
        this.setText(groupName);
        setCatFilterButtonListener();
        mAdapter = new CatFilterAdapter(mContext, list);
        initGridView();
    }

    private void initGridView() {
        mGridView = new GridView(mContext);
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);
        mGridView.setNumColumns(GridView.AUTO_FIT);
        mGridView.setAdapter(mAdapter);
    }

    private void setCatFilterButtonListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isExpand) {
                    initPopup();
                } else {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
                setArrow();
            }
        });
    }

    private void initPopup() {
        mPopupWindow = new PopupWindow();
        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        mPopupWindow.setContentView(mGridView);
        mPopupWindow.showAsDropDown(this);
    }

    private void setArrow() {
        Drawable arrow;
        if (isExpand) {
            arrow = getResources().getDrawable(R.mipmap.arrow2_up);
        } else {
            arrow = getResources().getDrawable(R.mipmap.arrow2_down);
        }
        arrow.setBounds(0, 0, arrow.getIntrinsicWidth(), arrow.getIntrinsicHeight());
        this.setCompoundDrawables(null, null, arrow, null);
        isExpand = !isExpand;
    }

    class CatFilterAdapter extends BaseAdapter {
        Context context;
        ArrayList<CategoryChildBean> list;

        public CatFilterAdapter(Context context, ArrayList<CategoryChildBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            CatFilterViewHolder vh = null;
            if (view == null) {
                view = View.inflate(context, R.layout.item_cat_filter, null);
                vh = new CatFilterViewHolder(view);
                view.setTag(vh);
            } else {
                vh = (CatFilterViewHolder) view.getTag();
            }
            vh.bind(i);
            return view;
        }

        class CatFilterViewHolder {
            @BindView(R.id.ivCategoryChildThumb)
            ImageView ivCategoryChildThumb;
            @BindView(R.id.tvCategoryChildName)
            TextView tvCategoryChildName;
            @BindView(R.id.LayoutCatFilter)
            RelativeLayout LayoutCatFilter;


            CatFilterViewHolder(View view) {
                ButterKnife.bind(this, view);
            }

            public void bind(final int position) {
                ImageLoader.downloadImg(context, ivCategoryChildThumb, list.get(position).getImageUrl());
                tvCategoryChildName.setText(list.get(position).getName());
                LayoutCatFilter.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MFGT.gotoCategoryChildDetail(mContext,
                                list.get(position).getId(),
                                GroupName,
                                list);
                        MFGT.finish((CategorychildDetailActivity) mContext);
                    }
                });
            }
        }


    }


}
