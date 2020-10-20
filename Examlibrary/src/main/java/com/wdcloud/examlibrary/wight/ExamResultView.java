package com.wdcloud.examlibrary.wight;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wdcloud.examlibrary.R;

import androidx.annotation.Nullable;

/**
 * Info:
 * Created by Umbrella.
 * CreateTime: 2020/9/28 14:20
 */
//考试结果弧形View
public class ExamResultView extends View {
    private Context context;
    private Paint mPaint = new Paint();   //画笔
    private RectF mRectF = new RectF();   //进度条rectf
    private int textColor;      //画笔
    private float textSize;     //字体大小
    private int progressWidth;  //进度宽度
    private int progressColor;   //进度颜色
    private int progressBackground;//背景颜色
    private float progressAlpa;  //进度透明度
    private int progressSize=60;   //环形大小
    private float mStartAngle;// 开始角度
    private float mSweepAngle;// 扫描角度
    private float mCurrentAngleSize; //当前进度
    private int maxProgress;//最大进度
    private long mDuration=1000;
    private String headText;
    private String firstText="合格";
    private String secondText="67分";
    public ExamResultView(Context context) {
        super(context);
    }

    public ExamResultView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initTypedArray(attrs);
    }

    public ExamResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void initTypedArray(AttributeSet attrs) {
        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExamResultView);
        textColor = a.getInt(R.styleable.ExamResultView_text_color, Color.parseColor("#97A3E6"));
        textSize = a.getDimension(R.styleable.ExamResultView_text_size, 80);
        progressWidth = a.getInteger(R.styleable.ExamResultView_progressWidth, dp2px(context,15));
        progressColor = a.getColor(R.styleable.ExamResultView_progressColor, Color.parseColor("#E85A3A"));
        progressBackground = a.getColor(R.styleable.ExamResultView_progressBackground, Color.parseColor("#FFEDE8"));
        mStartAngle=a.getFloat(R.styleable.ExamResultView_startAngle,135f);
        mSweepAngle=a.getFloat(R.styleable.ExamResultView_endAngle,270f);
        progressAlpa = a.getFloat(R.styleable.ExamResultView_progressAlpa, 12);
        mCurrentAngleSize=a.getFloat(R.styleable.ExamResultView_progress,200f);
        maxProgress=a.getInteger(R.styleable.ExamResultView_maxProgress,100);
        headText = a.getString(R.styleable.ExamResultView_headText);
        progressSize = a.getInteger(R.styleable.ExamResultView_outsideSize, 100);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        mRectF.left = progressWidth;
        mRectF.top = progressWidth;
        mRectF.right = centerX * 2 - progressWidth;
        mRectF.bottom = centerX * 2 - progressWidth;
        drawOutSideCircle(canvas,centerX);
        drawBackground(canvas);
        drawDial(canvas);
        drawFirstText(canvas,centerX);
        drawSecondText(canvas,centerX);
    }
    protected void drawDial(Canvas canvas){
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(progressColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(mRectF, mStartAngle, mCurrentAngleSize, false, mPaint);
        mPaint.setShader(null);
    }
    protected void drawOutSideCircle(Canvas canvas,int centerX)
    {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(getColorWithAlpha(0.3f,progressColor));
        RectF rectF = new RectF(2, 2, centerX*2 -2, centerX*2 - 2);
        canvas.drawArc(rectF, mStartAngle,270, false, mPaint);
    }
    //圆环内顶部文字
    public void setFirstText(String firstText)
    {
        this.firstText=firstText;
    }
    //圆环内底部文字
    public void setSencondText(String sencondText){
        this.secondText=sencondText;
    }
    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(float progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("Progress value can not be less than 0");
        }
        if (progress > maxProgress) {
            progress = maxProgress;
        }
        mCurrentAngleSize = progress;
        float size = mCurrentAngleSize / maxProgress;
        mCurrentAngleSize = (int) (mSweepAngle * size);
        setAnimator(0, mCurrentAngleSize);
    }
    protected void drawBackground(Canvas canvas) {
        //画笔的填充样式，Paint.Style.FILL 填充内部;Paint.Style.FILL_AND_STROKE 填充内部和描边;Paint.Style.STROKE 描边
        mPaint.setStyle(Paint.Style.STROKE);
        //圆弧的宽度
        mPaint.setStrokeWidth(progressWidth);
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        //画笔的颜色
        mPaint.setColor(progressBackground);
        //画笔的样式 Paint.Cap.Round 圆形,Cap.SQUARE 方形
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //开始画圆弧
        Log.e("圆弧数据",mRectF.toString());
        canvas.drawArc(mRectF, mStartAngle, 270, false, mPaint);
    }
    private void drawFirstText(Canvas canvas, float centerX) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#5F6368"));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(dp2px(context,15));
        Rect firstTextBounds = new Rect();
        paint.getTextBounds(firstText, 0, firstText.length(), firstTextBounds);
        canvas.drawText(firstText, centerX, firstTextBounds.height() / 2 + getHeight() * 2 / 5, paint);
    }
    private void drawSecondText(Canvas canvas, float centerX) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#E85A3A"));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(dp2px(context,25));
        Rect bounds = new Rect();
        paint.getTextBounds(secondText, 0, 3, bounds);
        canvas.drawText(secondText, centerX, getHeight() / 2 + bounds.height() / 2 +
                getFontHeight(secondText, 3)+30, paint);
    }
    private float getFontHeight(String textStr, float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Rect bounds = new Rect();
        paint.getTextBounds(textStr, 0, textStr.length(), bounds);
        return bounds.height();
    }
    /**
     * 对rgb色彩加入透明度
     * @param alpha     透明度，取值范围 0.0f -- 1.0f.
     * @param baseColor
     * @return a color with alpha made from base color
     */
    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 设置动画
     *
     * @param start  开始位置
     * @param target 结束位置
     */
    private void setAnimator(float start, float target) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, target);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setTarget(mCurrentAngleSize);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurrentAngleSize = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }
    //设置动画执行时间
    public void setAnimTime(long milli)
    {
        this.mDuration=milli;
    }
}
