package com.wdcloud.examlibrary.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import static com.wdcloud.examlibrary.adapter.AnswerSheetAdapter.TYPE_LEVEL_0;
import static com.wdcloud.examlibrary.adapter.AnswerSheetAdapter.TYPE_LEVEL_1;
import static com.wdcloud.examlibrary.adapter.AnswerSheetAdapter.TYPE_LEVEL_2;
import static com.wdcloud.examlibrary.adapter.AnswerSheetAdapter.TYPE_LEVEL_3;

/**
 * Info:
 * Created by Umbrella.
 * CreateTime: 2020/10/10 15:26
 */

public class AnswerBean implements MultiItemEntity {
    private String examTitle;
    private String isSelect="false";
    private String isAnswer="false";

    public AnswerBean(String examTitle, String isSelect, String isAnswer) {
        this.examTitle = examTitle;
        this.isSelect = isSelect;
        this.isAnswer = isAnswer;
    }

    public String getIsAnswer() {
        return isAnswer == null ? "" : isAnswer;
    }

    public void setIsAnswer(String isAnswer) {
        this.isAnswer = isAnswer == null ? "" : isAnswer;
    }

    public String getExamTitle() {
        return examTitle == null ? "" : examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle == null ? "" : examTitle;
    }

    public String getIsSelect() {
        return isSelect == null ? "" : isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect == null ? "" : isSelect;
    }

    @Override
    public int getItemType() {
        if(isAnswer.equals("true"))
        {
            if(isSelect.equals("true"))
            {
                return TYPE_LEVEL_0;
            }
            else
            {
                return TYPE_LEVEL_1;
            }
        }
        else
        {
            if(isSelect.equals("true"))
            {
                return TYPE_LEVEL_2;
            }
            else
            {
                return TYPE_LEVEL_3;
            }
        }
    }
}
