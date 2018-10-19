package com.jianjunhuang.lib.switchbutton;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Px;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

public class SwitchButton extends AppCompatTextView implements Animator.AnimatorListener {

  public static int INVALID_TEXT_COLOR = 0xffc4c8dc;
  public static int INVALID_BORDER_COLOR = 0xffc4c8dc;
  public static int INVALID_GB_COLOR = 0xfff0f0f0;

  public static int DEFAULT_TEXT_COLOR = Color.WHITE;
  public static int DEFAULT_BORDER_COLOR = 0xff303F9F;
  public static int DEFAULT_BG_COLOR = 0xff3F51B5;

  public static int CHECKED_TEXT_COLOR = Color.WHITE;
  public static int CHECKED_BORDER_COLOR = 0xffFF4081;
  public static int CHECKED_BG_COLOR = 0xffFF4081;

  @Px
  public static int DEFAULT_BORDER_WIDTH = 2;
  @Px
  public static int DEFAULT_RADIUS = 8;

  public static int DEFAULT_ANIMATE_DURATION = 400;

  private int mAnimateDuration = DEFAULT_ANIMATE_DURATION;

  private Paint mBorderPaint;
  private Paint mBgPaint;
  private Paint mAnimatePaint;
  @SwitchButtonStatus
  private int mStatus;

  private int mInvalidTextColor = INVALID_TEXT_COLOR;
  private int mInvalidBorderColor = INVALID_BORDER_COLOR;
  private int mInvalidBgColor = DEFAULT_BG_COLOR;

  private int mDefaultTextColor = DEFAULT_TEXT_COLOR;
  private int mDefaultBorderColor = DEFAULT_BORDER_COLOR;
  private int mDefaultBgColor = DEFAULT_BG_COLOR;

  private int mCheckedTextColor = CHECKED_TEXT_COLOR;
  private int mCheckedBorderColor = CHECKED_BORDER_COLOR;
  private int mCheckedBgColor = CHECKED_BG_COLOR;

  private int mBorderRadius = DEFAULT_RADIUS;
  private int mBorderWidth = DEFAULT_BORDER_WIDTH;

  private RectF mRectF;

  private float animateRadius;
  private ObjectAnimator mAnimator = ObjectAnimator.ofFloat(this, "animateRadius", 0);

  public SwitchButton(Context context) {
    this(context, null);
  }

  public SwitchButton(Context context, AttributeSet attrs) {
    this(context, attrs, -1);
  }

  private static final String TAG = "SwitchButton";

  public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.SwitchButton);

    if (typedArray != null) {
      mInvalidTextColor =
          typedArray.getColor(R.styleable.SwitchButton_invalidTextColor, INVALID_TEXT_COLOR);
      mInvalidBorderColor =
          typedArray.getColor(R.styleable.SwitchButton_invalidBorderColor, INVALID_BORDER_COLOR);
      mInvalidBgColor =
          typedArray.getColor(R.styleable.SwitchButton_invalidBackgroundColor, INVALID_GB_COLOR);

      mDefaultTextColor =
          typedArray.getColor(R.styleable.SwitchButton_defaultTextColor, DEFAULT_TEXT_COLOR);
      mDefaultBorderColor =
          typedArray.getColor(R.styleable.SwitchButton_defaultBorderColor, DEFAULT_BORDER_COLOR);
      mDefaultBgColor =
          typedArray.getColor(R.styleable.SwitchButton_defaultBackgroundColor, DEFAULT_BG_COLOR);

      mCheckedTextColor =
          typedArray.getColor(R.styleable.SwitchButton_checkedTextColor, CHECKED_TEXT_COLOR);
      mCheckedBorderColor =
          typedArray.getColor(R.styleable.SwitchButton_checkedBorderColor, CHECKED_BORDER_COLOR);
      mCheckedBgColor =
          typedArray.getColor(R.styleable.SwitchButton_checkedBackgroundColor, CHECKED_BG_COLOR);

      mBorderRadius =
          typedArray.getDimensionPixelSize(R.styleable.SwitchButton_radius, DEFAULT_RADIUS);
      mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.SwitchButton_borderWidth,
                                                      DEFAULT_BORDER_WIDTH);
      mAnimateDuration =
          typedArray.getInt(R.styleable.SwitchButton_animateDuration, DEFAULT_ANIMATE_DURATION);
      typedArray.recycle();
    }

    mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mBorderPaint.setStyle(Paint.Style.STROKE);
    mBorderPaint.setColor(mDefaultBorderColor);
    mBorderPaint.setStrokeWidth(mBorderWidth);

    mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mBgPaint.setColor(mDefaultBgColor);

    setTextColor(mDefaultTextColor);

    mRectF = new RectF();
    mAnimator.setDuration(mAnimateDuration);
    mAnimator.setInterpolator(new AccelerateInterpolator());

    mAnimatePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mAnimatePaint.setStyle(Paint.Style.FILL);

    mAnimator.addListener(this);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    mRectF.set(mBorderWidth, mBorderWidth, getWidth() - mBorderWidth, getHeight() - mBorderWidth);
    mAnimator.setFloatValues(0, getLen());
  }

  private float getLen() {
    return (float) Math.sqrt((getHeight() * getHeight() + getRight() * getRight())) + 10;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (mAnimator.isRunning()) {
      createCircleShader();
    }
    canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mBgPaint);
    canvas.drawRoundRect(mRectF, mBorderRadius, mBorderRadius, mBorderPaint);
    super.onDraw(canvas);
  }

  private float preX = 0;
  private float preY = 0;

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      preX = event.getX();
      preY = event.getY();
    }
    return super.onTouchEvent(event);
  }

  public int getInvalidTextColor() {
    return mInvalidTextColor;
  }

  public void setInvalidTextColor(int mInvalidTextColor) {
    this.mInvalidTextColor = mInvalidTextColor;
  }

  public int getInvalidBorderColor() {
    return mInvalidBorderColor;
  }

  public void setInvalidBorderColor(int mInvalidBorderColor) {
    this.mInvalidBorderColor = mInvalidBorderColor;
  }

  public int getIncalidBgColor() {
    return mInvalidBgColor;
  }

  public void setIncalidBgColor(int mInvalidBgColor) {
    this.mInvalidBgColor = mInvalidBgColor;
  }

  public int getDefaultTextColor() {
    return mDefaultTextColor;
  }

  public void setDefaultTextColor(int mDefaultTextColor) {
    this.mDefaultTextColor = mDefaultTextColor;
  }

  public int getDefaultBorderColor() {
    return mDefaultBorderColor;
  }

  public void setDefaultBorderColor(int mDefaultBorderColor) {
    this.mDefaultBorderColor = mDefaultBorderColor;
  }

  public int getDefaultBgColor() {
    return mDefaultBgColor;
  }

  public void setDefaultBgColor(int mDefaultBgColor) {
    this.mDefaultBgColor = mDefaultBgColor;
  }

  public int getCheckedTextColor() {
    return mCheckedTextColor;
  }

  public void setCheckedTextColor(int mCheckedTextColor) {
    this.mCheckedTextColor = mCheckedTextColor;
  }

  public int getCheckedBorderColor() {
    return mCheckedBorderColor;
  }

  public void setCheckedBorderColor(int mCheckedBorderColor) {
    this.mCheckedBorderColor = mCheckedBorderColor;
  }

  public int getCheckedBgColor() {
    return mCheckedBgColor;
  }

  public void setCheckedBgColor(int mCheckedBgColor) {
    this.mCheckedBgColor = mCheckedBgColor;
  }

  public int getBorderRadius() {
    return mBorderRadius;
  }

  public void setBorderRadius(int mBorderRadius) {
    this.mBorderRadius = mBorderRadius;
  }

  public int getBorderWidth() {
    return mBorderWidth;
  }

  public void setBorderWidth(int mBorderWidth) {
    this.mBorderWidth = mBorderWidth;
  }

  public void switchStatus(@SwitchButtonStatus int status) {
    switchStatus(status, false);
  }

  public int getStatus() {
    return mStatus;
  }

  public boolean isAnimateRunning() {
    return mAnimator.isRunning();
  }

  public int getAnimateDuration() {
    return mAnimateDuration;
  }

  public void setAnimateDuration(int mAnimateDuration) {
    this.mAnimateDuration = mAnimateDuration;
  }

  public void switchStatus(@SwitchButtonStatus int status, boolean animate) {

    this.mStatus = status;
    if (animate) {
      if (mAnimator.isRunning()) {
        mAnimator.cancel();
      }
      switch (mStatus) {
        case SwitchButtonStatus.CHECKED:
          mAnimatePaint.setColor(mCheckedBgColor);
          break;
        case SwitchButtonStatus.INVALID:
          mAnimatePaint.setColor(mInvalidBgColor);
          break;
        case SwitchButtonStatus.DEFAULT:
          mAnimatePaint.setColor(mDefaultBgColor);
          break;
      }
      mAnimator.start();
    } else {
      changeStyle();
    }
  }

  private void setAnimateRadius(float animateRadius) {
    this.animateRadius = animateRadius;
    invalidate();
  }

  private void createCircleShader() {
    Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    canvas.drawColor(mBgPaint.getColor());
    canvas.drawCircle(preX, preY, animateRadius, mAnimatePaint);
    mBgPaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
  }

  private void changeStyle() {
    mBgPaint.setShader(null);
    switch (mStatus) {
      case SwitchButtonStatus.CHECKED:
        mBgPaint.setColor(mCheckedBgColor);
        mBorderPaint.setColor(mCheckedBorderColor);
        setTextColor(mCheckedTextColor);
        break;
      case SwitchButtonStatus.INVALID:
        mBgPaint.setColor(mInvalidBgColor);
        mBorderPaint.setColor(mInvalidBorderColor);
        setTextColor(mInvalidTextColor);
        break;
      case SwitchButtonStatus.DEFAULT:
        mBgPaint.setColor(mDefaultBgColor);
        mBorderPaint.setColor(mDefaultBorderColor);
        setTextColor(mDefaultTextColor);
        break;
    }
  }


  @Override
  public void onAnimationStart(Animator animation) {

  }

  @Override
  public void onAnimationEnd(Animator animation) {
    changeStyle();
  }

  @Override
  public void onAnimationCancel(Animator animation) {
    changeStyle();
  }

  @Override
  public void onAnimationRepeat(Animator animation) {

  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    if (mAnimator != null && mAnimator.isRunning()) {
      mAnimator.cancel();
    }
  }
}
