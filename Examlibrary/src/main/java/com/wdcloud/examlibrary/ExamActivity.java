package com.wdcloud.examlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class ExamActivity extends FragmentActivity implements View.OnClickListener {
    private TextView textView;
    private MyViewPager examPager;
    private TextView examCurrentNum;
    private TextView examSumText;
    private TextView examCardText;
    private List<AnswerBean> answerBeans;
    private Dialog bottomDialog;
    private List<ExamRequsetBean> examRequsetBeans;
    private int currentIndex=0;
    private Button btExamCard;
    private Button btExamLast;
    private Button btExamNext;
    private List<ExamRequsetBean.RequsetBean> requsetBeanList;
    private TextView examTitle;
    private ImageView backImg;
    private LocalBroadcastManager lbm;
    private AnswerSheetAdapter answerSheetAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exam);
        TextView cardText = findViewById(R.id.exam_card_text);
        cardText.setVisibility(View.VISIBLE);
        textView = findViewById(R.id.exam_time_text);
        examCurrentNum = findViewById(R.id.exam_current_num);
        examSumText = findViewById(R.id.exam_sum_text);
        examCardText = findViewById(R.id.exam_card_text);
        btExamCard = findViewById(R.id.bottom_exam_card);
        btExamLast = findViewById(R.id.bottom_exam_last);
        btExamNext = findViewById(R.id.bottom_exam_next);
        examTitle = findViewById(R.id.exam_title_text);
        backImg = findViewById(R.id.exam_back_img);
        examCardText.setOnClickListener(this);
        btExamCard.setOnClickListener(this);
        btExamLast.setOnClickListener(this);
        btExamNext.setOnClickListener(this);
        backImg.setOnClickListener(this);
        startTime();
        initDialogData();
        initAnswerDialog();
        examPager = findViewById(R.id.exam_viewPager);
        examPager.setCurrentItem(0);
        examCurrentNum.setText("1");
        examSumText.setText("/20");
        ExamPagerAdapter examPagerAdapter = new ExamPagerAdapter(true,examRequsetBeans, getSupportFragmentManager());
        examPager.setAdapter(examPagerAdapter);
        examPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentIndex=position;
                examCurrentNum.setText((position+1)+"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        lbm = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.wdexam.jumptonext");
        filter.addAction("com.wdexam.jumptoposition");
        lbm.registerReceiver(mMessageReceiver, filter);
    }
    private void startTime()
    {
        handler.sendEmptyMessageDelayed(1, 1000);
    }
    private void endTime()
    {
        handler.removeCallbacksAndMessages(null);
    }
    //计时器任务
    int time = 100;
    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    time--;
                    textView.setText("倒计时："+time+"s");
                    if(time>0)
                    {
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
                default:
                    break;
            }

        };
    };

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.exam_card_text)
        {
            bottomDialog.show();
        }
        else if(id==R.id.bottom_exam_card)
        {
            bottomDialog.show();
        }else if(id==R.id.bottom_exam_last)
        {
            currentIndex--;
            if(currentIndex<0)
            {
                currentIndex=0;
            }
            examPager.setCurrentItem(currentIndex);
        }
        else if(id==R.id.bottom_exam_next)
        {
            currentIndex++;
            if(currentIndex<examRequsetBeans.size())
            {
                examPager.setCurrentItem(currentIndex);
            }
            else if(currentIndex>=examRequsetBeans.size())
            {
                currentIndex=examRequsetBeans.size();
                examPager.setCurrentItem(currentIndex);
                Toast.makeText(this,"已经是最后一题",Toast.LENGTH_SHORT).show();
            }
        }
        else if(id==R.id.exam_back_img)
        {
            finish();
            endTime();
        }
    }
    private void initDialogData()
    {
        answerBeans = new ArrayList<>();
        examRequsetBeans = new ArrayList<>();
        for (int i = 0; i <20; i++) {
            if(i%2==0)
            {
                answerBeans.add(new AnswerBean(i+1+"","false","true"));
            }
            else
            {
                answerBeans.add(new AnswerBean(i+1+"","false","true"));
            }
        }
        for (int i = 0; i <20; i++) {
            requsetBeanList = new ArrayList<>();
            if(i%2==0)
            {
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("A. 15mm",false,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("B. 钢筋混凝土梁的保护层厚度最小取保护",false,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("C. 17mm",false,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("D. 18mm",false,false));
                examRequsetBeans.add(new ExamRequsetBean((i+1)+"",false,true,true,(i+1)+". "+"一类环境中，钢筋混凝土梁的保护层厚度最小","true", requsetBeanList));
            }
            else
            {
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("A. 15mm",false,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("B. 16mm",false,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("C. 17mm",false,false));
                requsetBeanList.add(new ExamRequsetBean.RequsetBean("F. 18mm",false,false));
                examRequsetBeans.add(new ExamRequsetBean((i+1)+"",false,true,true,(i+1)+". "+"一类环境中，钢筋混凝土梁的保护层厚度最小","false", requsetBeanList));
            }
        }
    }
    public void initAnswerDialog()
    {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.answer_dialog_layout, null);
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ExamActivity.this,5);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        answerSheetAdapter = new AnswerSheetAdapter(examRequsetBeans);
        recyclerView.setAdapter(answerSheetAdapter);
        answerSheetAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent("com.wdexam.jumptoposition");
                intent.putExtra("index", position);
                lbm.sendBroadcast(intent);
                bottomDialog.dismiss();
            }
        });
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.wdexam.jumptonext")) {
                jumpToNext() ;
            } else if (intent.getAction().equals("com.wdexam.jumptoposition")) {
                int index = intent.getIntExtra("index", 0);
                jumpToPosition(index);
            }
        }
    };
    public void jumpToNext(){
        int currentItem = examPager.getCurrentItem();
        examPager.setCurrentItem(currentItem+1);
    }
    public void jumpToPosition(int index)
    {
        examPager.setCurrentItem(index,false);
    }
    @Override
    protected void onDestroy() {
        endTime();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                mMessageReceiver);
        super.onDestroy();
    }
    public void refreshDialog()
    {
        answerSheetAdapter.notifyDataSetChanged();
    }
}