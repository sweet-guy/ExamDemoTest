package com.wdcloud.examdemotest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.wdcloud.examdemotest.R;
import com.wdcloud.examlibrary.ExamActivity;
import com.wdcloud.examlibrary.ExamResultActivity;
import com.wdcloud.examlibrary.ScoreResultActivity;

import androidx.fragment.app.FragmentActivity;

/**
 * @version 1.0
 * @author hzc
 * @date 2015-6-24
 */
public class MainActivity extends FragmentActivity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Button btExam = findViewById(R.id.bt_exam);
		Button btResult = findViewById(R.id.bt_result);
		Button btResultView = findViewById(R.id.bt_result_view);
		btExam.setOnClickListener(this);
		btResult.setOnClickListener(this);
		btResultView.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_exam://考试界面
			Intent intent = new Intent(MainActivity.this,ExamActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_result://查看答案界面
			Intent resultIntent = new Intent(MainActivity.this, ExamResultActivity.class);
			startActivity(resultIntent);
			break;
		case R.id.bt_result_view://结果页面
			Intent resultView = new Intent(MainActivity.this, ScoreResultActivity.class);
			startActivity(resultView);
			break;
		default:
			break;
		}
	}
}
