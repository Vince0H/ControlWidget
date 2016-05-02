package the.vince.slidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author Vince
 * @time 2016/4/20 22:51
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public class SlidingMenuView extends ViewGroup {

    private Scroller mScroller;
    private View mLeft_view;
    private View mContent_view;
    private int mLeft_view_width;

    private float downX;
    private float downY;
    private long downTime;

    private boolean isLeftMenuOpen = false;// 记录左侧菜单是否打开
    private float mDownX;

    public SlidingMenuView(Context context) {
        super(context);
    }

    public SlidingMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //代码
        // 该对象完成 屏幕移动动画效果
        mScroller = new Scroller(getContext());
    }


    public void leftMenuToggle(){
        isLeftMenuOpen = !isLeftMenuOpen;
        actionUpProcess();
    }

    public void openLeftMenu(){
        isLeftMenuOpen = true;
        actionUpProcess();
    }


    /**
     * 关闭左侧菜单
     */
    public void closeLeftMenu(){
        isLeftMenuOpen = false;
        actionUpProcess();
    }
    /**
     * 1. 布局完成获得子控件
     */
    @Override
    protected void onFinishInflate() {
        // 布局文件解析完成的回调
        //获取左侧菜单
        mLeft_view = getChildAt(0);
        //获取内容布局
        mContent_view = getChildAt(1);

        //获取左侧菜单的宽
        //getLayoutParams() 子控件向父控件传递信息, 包含位置 高, 宽,
        mLeft_view_width = mLeft_view.getLayoutParams().width;
        super.onFinishInflate();
    }

    /**
     * 2. 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 1. 测量左侧菜单
        // int 32 位
        // 31 32位测量模式
        // UNSPECIFIED 随意 00 30个0
        // EXACTLY 精确 01 30个0
        // AT_MOST 指定最大值 10 30个0

        // 后30位 大小
        // 精确测量 测量出宽度
        // 模式= 模式(前2位) +　宽度(30)
        // 模式 EXACTLY 宽度：mLeft_View_Width
        int leftView_widthMeasureSpec = MeasureSpec.makeMeasureSpec(mLeft_view_width, MeasureSpec
                .EXACTLY);//带模式值
        mLeft_view.measure(leftView_widthMeasureSpec, heightMeasureSpec);

        //2 测量内容
        mContent_view.measure(widthMeasureSpec, heightMeasureSpec);

        //3 设置大小
        //把widthMeasureSpec 的模式值去掉

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize
                (heightMeasureSpec));

    }

    /**
     * 3. 布局
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 摆放content的文字
        int dis = 0;
        int left = 0 + dis;
        int top = 0;
        int right = mContent_view.getMeasuredWidth() + dis;
        int bottom = mContent_view.getMeasuredHeight();
        mContent_view.layout(left, top, right, bottom);

        //摆放左侧菜单位置
        int lv_left = -mLeft_view.getMeasuredWidth() + dis;
        int lv_top = 0;
        int lv_right = 0 + dis;
        int lv_bottom = mLeft_view.getMeasuredHeight();

        mLeft_view.layout(lv_left, lv_top, lv_right, lv_bottom);
    }

    /**
     * 1. 事件分发, 设置单击的效果
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // 按下
                downX = event.getX();
                downY = event.getY();
                downTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                // 松开
                // 单击 1. 同一个点位置 2，时间短
                float upX = event.getX();
                float upY = event.getY();

                long upTime = System.currentTimeMillis();//获取时间
                if (Math.abs(downX - upX) < 5 && Math.abs(downY - upX) < 5
                        && upTime - downTime < 500) {
                    //单击
                    //判断单击的位置
                    if (upX > mLeft_view.getMeasuredWidth() && upX > 100) {
                        //如果左侧菜单打开, 则关闭
                        if (isLeftMenuOpen) {
                            isLeftMenuOpen = false;
                            actionUpProcess();
                            return true;//事件终止
                        }
                    }
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);//进入onIntercepTouchEvent();
    }


    /**
     * 2. 事件拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 横向滑动 事件拦截
        // 纵向滑动 事件放行 传递给子控件

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 处理触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //处理拖动事件
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下
                mDownX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //移动事件
                touchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                //松开
                // 判断屏幕的坐标
                // 1. 获取屏幕的x坐标
                int scrollX = getScrollX();

                // 2. 判断位置
                // 如果scrollX坐标小于左侧菜单宽度的一半(负数)
                if (scrollX < -mLeft_view.getMeasuredWidth() / 2) {
                    isLeftMenuOpen = true; //左侧菜单打开
                } else {
                    isLeftMenuOpen = false; //左侧菜单关闭
                }

                // 处理触摸松开状态的事件
                actionUpProcess();
                break;
            default:
                break;
        }

        return true;//事件终止
    }

    /**
     * 处理坐标的变化, 在draw方法里面
     */
    @Override
    public void computeScroll() {
        //处理坐标的变化
        if(mScroller.computeScrollOffset()){
            //计算中
            int currX = mScroller.getCurrX();

            //把屏幕移动到currX位置
            scrollTo(currX,0);
            invalidate(); //触发draw方法
        }

        super.computeScroll();

    }


    private void touchMove(MotionEvent event) {
        //拖动
        float moveX = event.getX();

        //取反方向
        int dx = Math.round(mDownX - moveX); //四舍五入

        // 移动屏幕

        // scrollTo(20,0) 移动那个位置 绝对坐标 移动到20（横坐标）的位置
        // scrollBy(20, 0) 移动的相对位置 往右移动20个像素

        // 判断 拖动的位置是否越界
        int scrollX = getScrollX(); //获取屏幕的X坐标
        if (scrollX + dx < -mLeft_view.getMeasuredWidth()) {
            //左边界
            scrollTo(-mLeft_view.getMeasuredWidth(), 0);
        } else if (scrollX + dx > 0) {
            //右边界
            scrollTo(0, 0);
        } else {
            scrollBy(dx, 0);
        }

        //变成起点坐标
        mDownX = moveX;
    }


    private void actionUpProcess() {
        // scrollTo(-left_View.getMeasuredWidth(), 0);
        // 动画的效果
        // startX x起始位置
        // startY y起始位置
        // dx x的移动的值
        // dy y的移动的值
        // duration 移动的时间
        int startX = getScrollX(); //当前屏幕的位置
        int endX = -1; //记录目标的位置

        if(isLeftMenuOpen){
            //打开
            endX = -mLeft_view.getMeasuredWidth();

        }else {
            //关闭
            endX = 0;
        }
        int dx = endX - startX;
        int startY = 0;
        int dy = 0;
        int duration = Math.abs(dx)*5; //半秒

        //移动动画
        mScroller.startScroll(startX,startY,dx,dy,duration);

        invalidate(); // 绘制
    }


}
