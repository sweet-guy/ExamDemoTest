package com.wdcloud.examlibrary.adapter;

import com.wdcloud.examlibrary.entity.ExamRequsetBean;
import com.wdcloud.examlibrary.fragment.ExamFragment;
import com.wdcloud.examlibrary.fragment.LookExamFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Info:
 * Created by Umbrella.
 * CreateTime: 2020/10/9 15:25
 */

public class ExamPagerAdapter extends FragmentStatePagerAdapter {
    private List<ExamRequsetBean> examRequsetBeans;
    private boolean isExam;
    public ExamPagerAdapter(boolean isExam,List<ExamRequsetBean> examRequsetBeanList, @NonNull FragmentManager fm) {
        super(fm);
        examRequsetBeans=examRequsetBeanList;
        this.isExam=isExam;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ExamRequsetBean examRequsetBean = examRequsetBeans.get(position);
        if(isExam)
        {
            return new ExamFragment(position,examRequsetBean);
        }
        else
        {
            return new LookExamFragment(position,examRequsetBean);
        }
    }

    @Override
    public int getCount() {
        return examRequsetBeans.size();
    }
}
