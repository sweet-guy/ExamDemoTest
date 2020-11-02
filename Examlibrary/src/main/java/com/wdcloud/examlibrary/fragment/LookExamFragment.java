package com.wdcloud.examlibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.wdcloud.examlibrary.R;
import com.wdcloud.examlibrary.adapter.ExamRequestAdapter;
import com.wdcloud.examlibrary.entity.ExamRequsetBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Info:
 * Created by Umbrella.
 * CreateTime: 2020/10/12 16:10
 */

public class LookExamFragment extends Fragment {
    private int index;
    private ExamRequsetBean examRequsetBean;
    public LookExamFragment(int index, ExamRequsetBean examRequsetBean){
        this.index = index;
        this.examRequsetBean=examRequsetBean;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.look_exam_fragment_layout,container, false);
        RecyclerView examList = rootView.findViewById(R.id.rv_requeset_list);
        TextView titleText = rootView.findViewById(R.id.exam_title_text);
        titleText.setText(examRequsetBean.getExamTitle());
        LinearLayout result = rootView.findViewById(R.id.result_info_layout);
        if(index%2==0)
        {
            result.setVisibility(View.GONE);
        }
        else
        {
            result.setVisibility(View.VISIBLE);
        }
        List<ExamRequsetBean.RequsetBean> requsetBeanList =examRequsetBean.getRequsetBeanList();
        initExamList(examList,requsetBeanList);
        return rootView;
    }
    private void initExamList(RecyclerView recyclerView,List<ExamRequsetBean.RequsetBean> requsetBeanList)
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ExamRequestAdapter examRequestAdapter = new ExamRequestAdapter(requsetBeanList,false);
        recyclerView.setAdapter(examRequestAdapter);
        examRequestAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ExamRequsetBean.RequsetBean requsetBean = requsetBeanList.get(position);
                String isDouble = examRequsetBean.getIsDouble();
            }
        });
    }
}
