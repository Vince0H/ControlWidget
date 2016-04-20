package the.vince.togglelibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Vince
 * @time 2016/4/20 15:34
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public class ToggleView extends View {

    private Bitmap mBitmapBackgroud;
    private Bitmap mBitmapButton;
    private Paint mPaint;
    private float downOrMoveOrUpX;

    private static final int STATE_DOWN = 1; // 按下
    private static final int STATE_MOVE = 2; // 移动
    private static final int STATE_UP = 3; // 松开

    private int current_State = STATE_DOWN;// 0 记录事件的状态
    private boolean isToggleOn = false;//记录开关的状态

    private OnToggleChangeListener mOnToggleChangeListener;
    //事件回调声明
    public interface OnToggleChangeListener {
        public void onToggleChanged(ToggleView toggleView, boolean isToggleOn);
    }

    public void setOnToggleChangeListener(OnToggleChangeListener listener) {
        this.mOnToggleChangeListener = listener;
    }

    /**
     * 当前状态的开关
     *
     * @return
     */
    public  boolean isToggleOn() {
        return isToggleOn;
    }

    public ToggleView(Context context) {
        this(context,null);
    }

    public ToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

        mPaint = new Paint();
    }

    private void initView() {
        //获取 两张图片
        //按钮
        mBitmapBackgroud = BitmapFactory.decodeResource(getResources(), R.drawable
                .switch_background);

        mBitmapButton = BitmapFactory.decodeResource(getResources(), R.drawable
                .slide_button_background);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //控件的大小, 为图片的大小
        setMeasuredDimension(mBitmapBackgroud.getWidth(), mBitmapBackgroud.getHeight());
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //添加触摸事件
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下
                downOrMoveOrUpX = event.getX();
                current_State = STATE_DOWN;
                break;
            case MotionEvent.ACTION_MOVE:
                //移动
                downOrMoveOrUpX = event.getX();
                current_State = STATE_MOVE;
                break;
            case MotionEvent.ACTION_UP:
                // 松开
                downOrMoveOrUpX = event.getX();
                current_State = STATE_UP;
                //判断
                if (downOrMoveOrUpX < mBitmapBackgroud.getWidth()/2 && isToggleOn) {
                    //左一半
                    isToggleOn = false;
                    //调用接口
                    if (mOnToggleChangeListener != null) {
                        mOnToggleChangeListener.onToggleChanged(this, isToggleOn);
                    }
                } else if (downOrMoveOrUpX >= mBitmapBackgroud.getWidth() / 2 && !isToggleOn) {
                    isToggleOn = true;
                    //调用接口
                    if (mOnToggleChangeListener != null) {
                        mOnToggleChangeListener.onToggleChanged(this, isToggleOn);
                    }
                }
                break;
            default:
                break;
        }
        invalidate();// 触发onDraw 方法的调用
        return true;//自己消费事件
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景
        canvas.drawBitmap(mBitmapBackgroud, 0, 0, mPaint);
        //绘制button
        if (current_State == STATE_DOWN || current_State == STATE_MOVE) {
            //按下 移动
            if (downOrMoveOrUpX < mBitmapButton.getWidth() / 2) {
                //x坐标 小于 按钮的一半, 左边界
                canvas.drawBitmap(mBitmapButton, 0, 0, mPaint);

            } else if (downOrMoveOrUpX > (mBitmapBackgroud.getWidth() - mBitmapButton.getWidth()
                    / 2)) {
                //右边
                canvas.drawBitmap(mBitmapButton, mBitmapBackgroud.getWidth() - mBitmapButton
                        .getWidth(), 0, mPaint);
            } else {
                //绘制
                float left = downOrMoveOrUpX - mBitmapButton.getWidth() / 2;
                canvas.drawBitmap(mBitmapButton, left, 0, mPaint);
            }
        } else if (current_State == STATE_UP) {
            //松开
            // 左一半
            if (isToggleOn) {
                //打开, 右边
                canvas.drawBitmap(mBitmapButton, (mBitmapBackgroud.getWidth() - mBitmapButton.getWidth()), 0, mPaint);
            } else {
                //关闭, 左边
                canvas.drawBitmap(mBitmapButton, 0, 0, mPaint);
            }
        }
        super.onDraw(canvas);
    }
}
