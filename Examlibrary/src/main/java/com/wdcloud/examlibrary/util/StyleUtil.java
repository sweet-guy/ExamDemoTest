package com.wdcloud.examlibrary.util;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Info:修改样式工具
 * Created by Umbrella.
 * CreateTime: 2020/11/2 10:42
 */

public class StyleUtil {
    static StyleUtil styleUtil;

    public static StyleUtil getInstance() {
        if (styleUtil == null) {
            styleUtil = new StyleUtil();
        }
        return styleUtil;
    }

    //修改shape外环颜色
    public void setShapeStyle(View view, int StrokeWidth, String strokeColor) {
        GradientDrawable background = (GradientDrawable) view.getBackground();
        background.setStroke(StrokeWidth, Color.parseColor(strokeColor));
    }

    //修改shape背景颜色
    public void setShapeBackground(View view, String backgroundColor) {
        GradientDrawable background = (GradientDrawable) view.getBackground();
        background.setColor(Color.parseColor(backgroundColor));
    }

    public void setTextStyle(View view, String textColor, int textSize,int typeFace) {
        if (view instanceof Button) {
            Button button = (Button) view;
            button.setTextColor(Color.parseColor(textColor));
            button.setTextSize(textSize);
            button.setTypeface(Typeface.SERIF,typeFace);
        }
        else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTypeface(Typeface.SERIF,typeFace);
            textView.setTextColor(Color.parseColor(textColor));
            textView.setTextSize(textSize);
        }
    }
}
