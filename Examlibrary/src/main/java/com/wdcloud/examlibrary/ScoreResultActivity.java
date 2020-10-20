package com.wdcloud.examlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdcloud.examlibrary.wight.ExamResultView;

import org.w3c.dom.Text;

public class ScoreResultActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_score_result);
        ExamResultView examResultView = findViewById(R.id.examResultView);
        examResultView.setProgress(67);
        TextView cardText = findViewById(R.id.exam_card_text);
        cardText.setVisibility(View.GONE);
        ImageView backImg = findViewById(R.id.exam_back_img);
        Button btLookExam = findViewById(R.id.bt_look_exam);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btLookExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}