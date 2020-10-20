package com.wdcloud.examlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ToMainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_main);
        Button btExam = findViewById(R.id.bt_exam);
        Button btResult = findViewById(R.id.bt_result);
        Button btResultView = findViewById(R.id.bt_result_view);
        btExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToMainActivity.this,ExamActivity.class);
                startActivity(intent);
            }
        });
        btResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToMainActivity.this,ExamResultActivity.class);
                startActivity(intent);
            }
        });
        btResultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToMainActivity.this,ScoreResultActivity.class);
                startActivity(intent);
            }
        });
    }
}