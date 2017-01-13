package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.adapter.CategoryAdapter;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.net.IModelCategory;
import cn.ucai.fulicenter.model.net.ModelCategory;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    @BindView(R.id.elv_category)
    ExpandableListView elvcategory;
    @BindView(R.id.tv_nomore)
    TextView tvNomore;
    IModelCategory model;
    CategoryAdapter mAdapter;
    int groupCount;
    ArrayList<CategoryGroupBean> groupList=new ArrayList<>();
    ArrayList<ArrayList<CategoryChildBean>> childList=new ArrayList<>();
    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this,layout);
        mAdapter=new CategoryAdapter(childList,groupList,getContext());
        elvcategory.setAdapter(mAdapter);
        initView(false);
        initData();
        return layout;
    }

    private void initData() {
        model=new ModelCategory();
        model.downData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if(result!=null){
                    initView(true);
                    ArrayList<CategoryGroupBean> list = ConvertUtils.array2List(result);
                    groupList.addAll(list);
                    for(int i=0;i<list.size();i++){
                        downloadChildData(list.get(i).getId());
                    }
                }else {
                    initView(false);
                }
            }

            

            @Override
            public void onError(String error) {
                initView(false);
                Log.e("tag","error"+error);
            }
        });
    }

    private void downloadChildData(int id) {
        model.downData(getContext(), id, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if(result!=null){
                    ArrayList<CategoryChildBean> List = ConvertUtils.array2List(result);
                    childList.add(List);
                }
                if(groupCount==groupList.size()){
                    mAdapter.initData(groupList,childList);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView(boolean hasData) {
        tvNomore.setVisibility(hasData?View.GONE:View.VISIBLE);
        elvcategory.setVisibility(hasData?View.VISIBLE:View.GONE);
    }

}
