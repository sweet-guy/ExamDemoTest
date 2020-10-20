package com.wdcloud.examlibrary.wight;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Info:
 * Created by Umbrella.
 * CreateTime: 2020/10/13 11:39
 */

public class MyViewPager extends ViewPager {
    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }
    @Override
    public void setCurrentItem(int item,boolean needAnim) {
        super.setCurrentItem(item,false);
    }
}
