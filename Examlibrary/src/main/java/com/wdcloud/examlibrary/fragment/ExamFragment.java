package com.wdcloud.examlibrary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.wdcloud.examlibrary.ExamActivity;
import com.wdcloud.examlibrary.R;
import com.wdcloud.examlibrary.adapter.AnswerSheetAdapter;
import com.wdcloud.examlibrary.adapter.ExamRequestAdapter;
import com.wdcloud.examlibrary.entity.ExamRequsetBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Info:
 * Created by Umbrella.
 * CreateTime: 2020/10/9 16:59
 */

public class ExamFragment extends Fragment {
    private int index;
    private ExamRequsetBean examRequsetBean;
    LocalBroadcastManager mLocalBroadcastManager;
    public ExamFragment(int index, ExamRequsetBean examRequsetBean){
        this.index = index;
        this.examRequsetBean=examRequsetBean;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        View rootView = inflater.inflate(R.layout.exam_list_layout,container, false);
        RecyclerView examList = rootView.findViewById(R.id.rv_requeset_list);
        TextView titleText = rootView.findViewById(R.id.exam_title_text);
        titleText.setText(examRequsetBean.getExamTitle());
        List<ExamRequsetBean.RequsetBean> requsetBeanList =examRequsetBean.getRequsetBeanList();
        initExamList(examList,requsetBeanList);
        return rootView;
    }
    private void initExamList(RecyclerView recyclerView,List<ExamRequsetBean.RequsetBean> requsetBeanList)
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ExamRequestAdapter examRequestAdapter = new ExamRequestAdapter(requsetBeanList,true);
        recyclerView.setAdapter(examRequestAdapter);
        examRequestAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                String isDouble = examRequsetBean.getIsDouble();
                ExamRequsetBean.RequsetBean itemRequsetBean = requsetBeanList.get(position);
                boolean select = itemRequsetBean.isSelect();
                if(isDouble.equals("true"))
                {
                    select=!select;
                    itemRequsetBean.setSelect(select);
                    examRequsetBean.setHasSelect(false);
                    examRequestAdapter.notifyDataSetChanged();
                    for (ExamRequsetBean.RequsetBean requsetBean : requsetBeanList) {
                        if(requsetBean.isSelect())
                        {
                            examRequsetBean.setHasSelect(true);
                        }
                    }
//                    Toast.makeText(getContext(),"多选",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    for (ExamRequsetBean.RequsetBean requsetBean : requsetBeanList) {
                        requsetBean.setSelect(false);
                        examRequsetBean.setHasSelect(true);
                    }
                    requsetBeanList.get(position).setSelect(true);
                    examRequestAdapter.notifyDataSetChanged();
                    Intent intent = new Intent("com.wdexam.jumptonext");
                    mLocalBroadcastManager.sendBroadcast(intent);
                }
                ExamActivity examActivity = (ExamActivity) getContext();
                examActivity.refreshDialog();
            }
        });
    }
}
