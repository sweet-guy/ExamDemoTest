package com.wdcloud.examlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.wdcloud.examlibrary.adapter.AnswerSheetAdapter;
import com.wdcloud.examlibrary.adapter.ExamPagerAdapter;
import com.wdcloud.examlibrary.entity.AnswerBean;
import com.wdcloud.examlibrary.entity.ExamRequsetBean;
import com.wdcloud.examlibrary.wight.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class ExamResultActivity extends FragmentActivity implements View.OnClickListener {
    private int currentIndex=0;
    private TextView currentNum;
    private Button btExamCard;
    private Button btExamLast;
    private Button btExamNext;
    private MyViewPager resultViewPager;
    private TextView sumNum;
    private Dialog bottomDialog;
    private List<AnswerBean> answerBeans;
    private List<ExamRequsetBean> examRequsetBeans;
    private List<ExamRequsetBean.RequsetBean> requsetBeanList;
    private LocalBroadcastManager lbm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exam_result);
        TextView cardText = findViewById(R.id.exam_card_text);
        cardText.setVisibility(View.VISIBLE);
        ImageView backImg=findViewById(R.id.exam_back_img);
        currentNum = findViewById(R.id.exam_result_current_num);
        sumNum = findViewById(R.id.exam_result_sum_text);
        resultViewPager = findViewById(R.id.exam_result_viewPager);
        //下方答题卡下一题按钮
        btExamCard = findViewById(R.id.bottom_exam_card);
        btExamLast = findViewById(R.id.bottom_exam_last);
        btExamNext = findViewById(R.id.bottom_exam_next);
        btExamCard.setOnClickListener(this);
        btExamLast.setOnClickListener(this);
        btExamNext.setOnClickListener(this);
        cardText.setOnClickListener(this);
        backImg.setOnClickListener(this);
        resultViewPager.setCurrentItem(0);
        currentNum.setText("1");
        sumNum.setText("/20");
        initDialogData();
        initAnswerDialog();
        ExamPagerAdapter examPagerAdapter = new ExamPagerAdapter(false,examRequsetBeans, getSupportFragmentManager());
        resultViewPager.setAdapter(examPagerAdapter);
        resultViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex=position;
                currentNum.setText((position+1)+"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        lbm = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.wdexam.result.jumpposition");
        lbm.registerReceiver(mMessageReceiver, filter);
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
         if (intent.getAction().equals("com.wdexam.result.jumpposition")) {
                int index = intent.getIntExtra("index", 0);
                jumpToPosition(index);
            }
        }
    };
    public void jumpToPosition(int index)
    {
        resultViewPager.setCurrentItem(index,false);
    }
    private void initDialogData()
    {
        answerBeans = new ArrayList<>();
        examRequsetBeans = new ArrayList<>();
        for (int i = 0; i <20; i++) {
            if(i%2==0)
            {
                answerBeans.add(new AnswerBean(i+1+"","true","false"));
            }
            else
            {
                answerBeans.add(new AnswerBean(i+1+"","false","false"));
            }
        }
        for (int i = 0; i <20; i++) {
            requsetBeanList = new ArrayList<>();
            if(i%2==0)
            {
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("A. 15mm",true,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("B. 钢筋混凝土梁的保护层厚度最小取保护",false,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("C. 17mm",false,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("D. 18mm",false,false));
                examRequsetBeans.add(new ExamRequsetBean((i+1)+"",true,false,true,(i+1)+". "+"一类环境中，钢筋混凝土梁的保护层厚度最小","true", requsetBeanList));
            }
            else
            {
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("A. 15mm",false,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("B. 16mm",false,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("C. 17mm",true,true));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("F. 18mm",false,false));
                examRequsetBeans.add(new ExamRequsetBean((i+1)+"",true,false,false,(i+1)+". "+"一类环境中，钢筋混凝土梁的保护层厚度最小","true", requsetBeanList));
            }
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.bottom_exam_card)
        {
            bottomDialog.show();
        }
        else if(id==R.id.exam_card_text)
        {
            bottomDialog.show();
        }else if(id==R.id.bottom_exam_last)
        {
            currentIndex--;
            if(currentIndex<0)
            {
                currentIndex=0;
            }
            resultViewPager.setCurrentItem(currentIndex);
        }
        else if(id==R.id.bottom_exam_next)
        {
            currentIndex++;
            if(currentIndex<examRequsetBeans.size())
            {
                resultViewPager.setCurrentItem(currentIndex);
            }
            else if(currentIndex>=examRequsetBeans.size())
            {
                currentIndex=examRequsetBeans.size();
                resultViewPager.setCurrentItem(currentIndex);
                Toast.makeText(this,"已经是最后一题",Toast.LENGTH_SHORT).show();
            }
        }
        else if(id==R.id.exam_back_img)
        {
            finish();
        }
    }
    public void initAnswerDialog()
    {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.result_dialog_layout, null);
        RecyclerView examList = contentView.findViewById(R.id.rv_exam_list);
        initRecycle(examList);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width =getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
    }
    private void initRecycle(RecyclerView recyclerView)
    {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ExamResultActivity.this,5);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        AnswerSheetAdapter answerSheetAdapter = new AnswerSheetAdapter(examRequsetBeans);
        recyclerView.setAdapter(answerSheetAdapter);
        answerSheetAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent("com.wdexam.result.jumpposition");
                intent.putExtra("index", position);
                lbm.sendBroadcast(intent);
                bottomDialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mMessageReceiver);
        super.onDestroy();
    }
}