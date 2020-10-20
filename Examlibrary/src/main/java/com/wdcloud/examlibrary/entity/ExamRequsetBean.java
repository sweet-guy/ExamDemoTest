package com.wdcloud.examlibrary.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

import static com.wdcloud.examlibrary.adapter.AnswerSheetAdapter.TYPE_LEVEL_0;
import static com.wdcloud.examlibrary.adapter.AnswerSheetAdapter.TYPE_LEVEL_1;
import static com.wdcloud.examlibrary.adapter.AnswerSheetAdapter.TYPE_LEVEL_2;
import static com.wdcloud.examlibrary.adapter.AnswerSheetAdapter.TYPE_LEVEL_3;
import static com.wdcloud.examlibrary.adapter.ExamRequestAdapter.EXAM_TYPE_1;
import static com.wdcloud.examlibrary.adapter.ExamRequestAdapter.EXAM_TYPE_2;
import static com.wdcloud.examlibrary.adapter.ExamRequestAdapter.EXAM_TYPE_3;

/**
 * Info:
 * Created by Umbrella.
 * CreateTime: 2020/10/12 11:17
 */

public class ExamRequsetBean implements MultiItemEntity{
    private String examNum;
    private boolean hasSelect;
    private boolean isAnswer;
    private boolean isSure;
    private String examTitle;
    private String isDouble="false";
    private List<RequsetBean> requsetBeanList;

    public ExamRequsetBean(String examNum, boolean hasSelect, boolean isAnswer, boolean isSure, String examTitle, String isDouble, List<RequsetBean> requsetBeanList) {
        this.examNum = examNum;
        this.hasSelect = hasSelect;
        this.isAnswer = isAnswer;
        this.isSure = isSure;
        this.examTitle = examTitle;
        this.isDouble = isDouble;
        this.requsetBeanList = requsetBeanList;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public boolean isSure() {
        return isSure;
    }

    public void setSure(boolean sure) {
        isSure = sure;
    }

    public String getExamNum() {
        return examNum == null ? "" : examNum;
    }

    public void setExamNum(String examNum) {
        this.examNum = examNum == null ? "" : examNum;
    }

    public boolean isHasSelect() {
        return hasSelect;
    }

    public void setHasSelect(boolean hasSelect) {
        this.hasSelect = hasSelect;
    }

    public String getIsDouble() {
        return isDouble == null ? "" : isDouble;
    }

    public void setIsDouble(String isDouble) {
        this.isDouble = isDouble == null ? "" : isDouble;
    }

    public String getExamTitle() {
        return examTitle == null ? "" : examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle == null ? "" : examTitle;
    }

    public List<RequsetBean> getRequsetBeanList() {
        if (requsetBeanList == null) {
            return new ArrayList<>();
        }
        return requsetBeanList;
    }

    public void setRequsetBeanList(List<RequsetBean> requsetBeanList) {
        this.requsetBeanList = requsetBeanList;
    }

    @Override
    public int getItemType() {
        if(isAnswer)
        {
            if(hasSelect)
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
            if(isSure)
            {
                return TYPE_LEVEL_2;
            }
            else
            {
                return TYPE_LEVEL_3;
            }
        }
    }

    public static class RequsetBean implements MultiItemEntity{
        private String requestTitle;
        private boolean isSelect;
        private boolean isErro;

        public RequsetBean(String requestTitle, boolean isSelect, boolean isErro) {
            this.requestTitle = requestTitle;
            this.isSelect = isSelect;
            this.isErro = isErro;
        }

        public String getRequestTitle() {
            return requestTitle == null ? "" : requestTitle;
        }

        public void setRequestTitle(String requestTitle) {
            this.requestTitle = requestTitle == null ? "" : requestTitle;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isErro() {
            return isErro;
        }

        public void setErro(boolean erro) {
            isErro = erro;
        }

        @Override
        public int getItemType() {
            if(isSelect==true&&isErro==true)
            {
                return EXAM_TYPE_1;
            }
            else if(isSelect==true&&isErro==false)
            {
                return EXAM_TYPE_2;
            }
            else
            {
                return EXAM_TYPE_3;
            }
        }
    }
}
