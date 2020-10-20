package com.wdcloud.examlibrary.adapter;

import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.wdcloud.examlibrary.R;
import com.wdcloud.examlibrary.entity.AnswerBean;
import com.wdcloud.examlibrary.entity.ExamRequsetBean;
import com.wdcloud.examlibrary.util.ScreenUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Info:
 * Created by Umbrella.
 * CreateTime: 2020/10/12 11:13
 */

public class ExamRequestAdapter extends BaseMultiItemQuickAdapter<ExamRequsetBean.RequsetBean, BaseViewHolder> {
    public static final int EXAM_TYPE_1=1;
    public static final int EXAM_TYPE_2=2;
    public static final int EXAM_TYPE_3=3;
    private boolean isExam;
    public ExamRequestAdapter(@Nullable List<ExamRequsetBean.RequsetBean> data,boolean isExam) {
        super(data);
        this.isExam=isExam;
        addItemType(EXAM_TYPE_1, R.layout.bg_exam_erro);
        addItemType(EXAM_TYPE_2,R.layout.bg_exam_select);
        addItemType(EXAM_TYPE_3,R.layout.bg_exam_noselect);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ExamRequsetBean.RequsetBean examRequsetBean) {
        switch (baseViewHolder.getItemViewType()) {
            case EXAM_TYPE_1:
                baseViewHolder.setText(R.id.exam_erro_title,examRequsetBean.getRequestTitle());
//                View view = baseViewHolder.getView(R.id.rv_parent_exam_erro);
                break;
            case EXAM_TYPE_2:
                baseViewHolder.setText(R.id.exam_select_title,examRequsetBean.getRequestTitle());
                break;
            case EXAM_TYPE_3:
                if(isExam)
                {
                    RelativeLayout view = baseViewHolder.getView(R.id.rv_parent_noselect);
                    view.setPadding(ScreenUtil.dp2px(getContext(),20),ScreenUtil.dp2px(getContext(),13),ScreenUtil.dp2px(getContext(),20),ScreenUtil.dp2px(getContext(),13));
                }
                baseViewHolder.setText(R.id.exam_item_title,examRequsetBean.getRequestTitle());
                break;
        }
    }
}
