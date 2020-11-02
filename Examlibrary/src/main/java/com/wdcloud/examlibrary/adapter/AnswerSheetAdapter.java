package com.wdcloud.examlibrary.adapter;


import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.wdcloud.examlibrary.R;
import com.wdcloud.examlibrary.entity.ExamRequsetBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Info:
 * Created by Umbrella.
 * CreateTime: 2020/10/10 15:25
 */

public class AnswerSheetAdapter extends BaseMultiItemQuickAdapter<ExamRequsetBean, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_LEVEL_2 = 2;
    public static final int TYPE_LEVEL_3 = 3;

    public AnswerSheetAdapter(@Nullable List<ExamRequsetBean> data) {
        super(data);
        addItemType(TYPE_LEVEL_0,R.layout.answer_item_noselect_layout);
        addItemType(TYPE_LEVEL_1,R.layout.answer_item_select_layout);
        addItemType(TYPE_LEVEL_2,R.layout.answer_item_sure_layout);
        addItemType(TYPE_LEVEL_3,R.layout.answer_item_erro_layout);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ExamRequsetBean answerBean) {
        switch (baseViewHolder.getItemViewType())
        {
            case TYPE_LEVEL_0:
                baseViewHolder.setText(R.id.exam_text_num,answerBean.getExamNum());
                break;
            case TYPE_LEVEL_1:
                baseViewHolder.setText(R.id.exam_text_num,answerBean.getExamNum());
                break;
            case TYPE_LEVEL_2:
                baseViewHolder.setText(R.id.exam_sure_num,answerBean.getExamNum());
                break;
            case TYPE_LEVEL_3:
                baseViewHolder.setText(R.id.exam_erro_num,answerBean.getExamNum());
                break;
        }
    }
}
