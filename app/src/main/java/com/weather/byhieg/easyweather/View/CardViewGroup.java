package com.weather.byhieg.easyweather.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.byhieglibrary.Utils.LogUtils;
import com.example.byhieglibrary.Utils.ScreenUtil;

/**
 * Created by shiqifeng on 2016/9/12.
 * Mail:byhieg@gmail.com
 */
public class CardViewGroup extends ViewGroup{


    private int screenWidth;
    private int screenHeight;
    private int lastY;
    private int height,width;
    private int mStart,mEnd;
    private Scroller scroller;
    private int distance;

    public CardViewGroup(Context context) {
        super(context);
        init(context);
    }

    public CardViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        screenWidth = ScreenUtil.getScreenW(context);
        screenHeight = ScreenUtil.getScreenH(context);
        scroller = new Scroller(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //开始处理wrap_content,如果一个子元素都没有，就设置为0
        if (getChildCount() == 0) {
            setMeasuredDimension(0,0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //ViewGroup，宽，高都是wrap_content，根据我们的需求，宽度是子控件的宽度，高度则是所有子控件的总和
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            int childHeight = childOne.getMeasuredHeight();
            setMeasuredDimension(childWidth, childHeight * getChildCount());
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //ViewGroup的宽度为wrap_content,则高度不需要管，宽度还是自控件的宽度
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            setMeasuredDimension(childWidth,heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //ViewGroup的高度为wrap_content,则宽度不需要管，高度为子View的高度和
            View childOne = getChildAt(0);
            int childHeight = childOne.getMeasuredHeight();
            setMeasuredDimension(widthSize, childHeight * getChildCount());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int height = 0;
        int count = getChildCount();
        for(int i = 0;i < count;i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams)child.getLayoutParams();
            int leftMargin = params.leftMargin;
            int topMargin = params.topMargin;
            int rightMargin = params.rightMargin;
            int bottomMargin = params.bottomMargin;
            height += topMargin;
            child.layout(leftMargin,height,screenWidth - rightMargin,height + child.getMeasuredHeight());
            height += child.getMeasuredHeight() + bottomMargin;
        }

        this.height = height;

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStart = getScrollY();
                LogUtils.e("down","downnnnnnnnnnnnnnnnnnnnnnnn");
                lastY = y;
                if (scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = lastY - y;
                LogUtils.e("move",deltaY + "");
                if (isMove(deltaY)) {
                    scrollBy(0,deltaY);
                }
                lastY = y;
                break;

            case MotionEvent.ACTION_UP:
                int dScrollY = checkAlignment();
                if (dScrollY >= 0) {
                    scroller.startScroll(0,getScrollY(),0,- dScrollY);
                }
                break;

        }
        invalidate();
        return true;
    }

    private boolean isMove(int deltaY) {
        int scrollY = getScrollY();
        //从上向下滑动,滑动到最顶部就不能滑了
        if (deltaY < 0) {
            if(scrollY < 0){
                return false;
            } else if (deltaY + scrollY < 0) {
                scrollTo(0,0);
                return false;
            }
        }
        int bottomY = this.height + 20;
        LogUtils.e("bottomY",bottomY + "");
        LogUtils.e("height",getMeasuredHeight() + "");
        if (deltaY >= 0) {
            LogUtils.e("scrollY", scrollY + "");
            if (scrollY >= bottomY - getMeasuredHeight()) {
                LogUtils.e("cha",(bottomY - getMeasuredHeight()) + "");
                distance = scrollY - (bottomY - getMeasuredHeight());
                return false;
            }
        }
        return true;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            invalidate();
        }
    }

    private int checkAlignment(){
        mEnd = getScrollY();
        //
        boolean isUp = ((mEnd - mStart) > 0);
        if (isUp) {
            return 0;
        }else{
            return distance;
        }
    }
}
