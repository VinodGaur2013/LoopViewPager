package com.baiiu.loopviewpager.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.baiiu.loopviewpager.R;
import com.baiiu.loopviewpager.view.looping.LoopingViewPager;

/**
 * auther: baiiu
 * time: 16/3/27 27 17:42
 * description:
 */
public class SimpleCircleIndicator extends View implements ViewPager.OnPageChangeListener, IPageIndicator {

    /**
     * 点之间的距离
     */
    private int mDotInterval = 30;

    /**
     * 点的半径
     */
    private int mDotRadius = 10;

    private int mSelectedColor;
    private Paint mSelectedPaint;

    private int mUnSelectedColor;
    private Paint mUnSelectedPaint;

    private int mSelectedPosition;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    public SimpleCircleIndicator(Context context) {
        this(context, null);
    }

    public SimpleCircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SimpleCircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleCircleIndicator);
            mDotInterval = (int) typedArray.getDimension(R.styleable.SimpleCircleIndicator_dot_interval, 40);
            mDotRadius = (int) typedArray.getDimension(R.styleable.SimpleCircleIndicator_dot_radius, 10);
            mSelectedColor = typedArray.getColor(R.styleable.SimpleCircleIndicator_selected_color, Color.RED);
            mUnSelectedColor = typedArray.getColor(R.styleable.SimpleCircleIndicator_unselected_color, Color.WHITE);
            typedArray.recycle();
        }

        mSelectedPaint = new Paint();
        mSelectedPaint.setStyle(Paint.Style.FILL);
        mSelectedPaint.setAntiAlias(true);
        mSelectedPaint.setColor(mSelectedColor);

        mUnSelectedPaint = new Paint();
        mUnSelectedPaint.setStyle(Paint.Style.FILL);
        mUnSelectedPaint.setAntiAlias(true);
        mUnSelectedPaint.setColor(mUnSelectedColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);

        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);


        int width_result;
        int height_result;

        if (width_mode == MeasureSpec.EXACTLY) {
            width_result = width_size;

            if (height_mode == MeasureSpec.EXACTLY) {
                height_result = height_size;
            } else {
                height_result = mDotRadius * 2;
            }
        } else {
            int mCount = mAdapter.getCount();
            width_result = (mCount - 1) * mDotInterval + mCount * mDotRadius * 2;

            if (height_mode == MeasureSpec.EXACTLY) {
                height_result = height_size;
            } else {
                height_result = mDotRadius * 2;
            }

        }

        setMeasuredDimension(width_result, height_result);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int measuredWidth = getWidth();

        int mCount = mAdapter.getCount();
        int mDotTotalWidth = (mCount - 1) * mDotInterval + mCount * mDotRadius * 2;
        int mFirstDotXCoordinate = (int) ((measuredWidth - mDotTotalWidth) / 2F + 0.5) + mDotRadius;

        int measuredHeight = getHeight();
        int mDotYCoordinate = (int) ((measuredHeight - mDotRadius * 2) / 2F + 0.5) + mDotRadius;


        int x = mFirstDotXCoordinate;

        for (int i = 0; i < mCount; ++i) {
            if (i == mSelectedPosition) {
                canvas.drawCircle(x, mDotYCoordinate, mDotRadius, mSelectedPaint);
            } else {
                canvas.drawCircle(x, mDotYCoordinate, mDotRadius, mUnSelectedPaint);
            }
            x += mDotInterval + mDotRadius * 2;
        }
    }

    @Override
    public void setViewPager(ViewPager viewPager) {
        setViewPager(viewPager, 0);
    }

    @Override
    public void setViewPager(ViewPager viewPager, int initialPosition) {

        if (viewPager instanceof LoopingViewPager) {
            LoopingViewPager loopingViewPager = (LoopingViewPager) viewPager;
            mAdapter = loopingViewPager.getRealAdapter();
            loopingViewPager.setOnPageChangeListener(this);
        } else {
            mAdapter = viewPager.getAdapter();
            viewPager.addOnPageChangeListener(this);
        }

        if (mAdapter == null) {
            throw new IllegalStateException("ViewPager must initial first");
        }

        this.mViewPager = viewPager;

        mSelectedPosition = initialPosition;
        viewPager.setCurrentItem(initialPosition);

        invalidate();
    }

    @Override
    public void setCurrentItem(int item) {
        mSelectedPosition = item;
        if (mViewPager != null) {
            mViewPager.setCurrentItem(item);
        }

        invalidate();
    }

    @Override
    public void notifyDataSetChanged() {
        invalidate();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.mSelectedPosition = position;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
