package the.vince.youkulibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author Vince
 * @time 2016-04-13 下午 07:02
 * @updateAuthor $Author$
 * @updateDate $Date$
 */


public class UkuMenuView extends RelativeLayout {
    private RelativeLayout rl_level1;
    private RelativeLayout rl_level2;
    private RelativeLayout rl_level3;
    private ImageView iv_menu;
    private ImageView iv_home;

    // 记录level1显示或隐藏的状态
    private boolean isLevel1Show = true;
    // 记录level2显示或隐藏的状态
    private boolean isLevel2Show = true;
    // 记录level3显示或隐藏的状态
    private boolean isLevel3Show = true;

    //记录动画播放状态
    private int animCount = 0;

    public UkuMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //布局文件
        //初始化界面
        initView();
        initEvent();
    }

    public UkuMenuView(Context context) {
        super(context);
        //代码调用
    }

    private void initEvent() {
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            /*    switch (v.getId()) {
                    case R.id.iv_home:
                        homeClick();
                        break;
                    case R.id.iv_menu:
                        menuClick();
                        break;
                    default:
                        break;
                }*/
                if (v.getId() == R.id.iv_home) {
                    homeClick();
                } else if (v.getId() == R.id.iv_menu) {
                    menuClick();
                }
            }


        };
        //初始化事件
        //给menu菜单添加事件
        iv_menu.setOnClickListener(listener);
        //给home菜单添加事件
        iv_home.setOnClickListener(listener);
    }

    /**
     * 菜单事件的点击功能
     */
    private void menuClick() {
        //让第三层显示或隐藏
        isLevel3Show = !isLevel3Show;
        if (isLevel3Show) {
            //开第三层
            openLevel(rl_level3, 0);
        } else {
            closeLevel(rl_level3, 0);
        }
    }

    /**
     * home菜单事件功能
     */
    private void homeClick() {
        /*
         * 控制第二层和第三层的操作 1. 如果都不显示，只打开第二层 2. 如果都显示，都关闭 3. 如果第二层显示，第二层关闭
		 */
        if (!isLevel2Show && !isLevel3Show) {// 1. 如果都不显示
            //只打开第二层
            openLevel(rl_level2, 0);
        } else if (isLevel2Show && isLevel3Show) {//2. 如果都显示
            //都关闭
            closeLevel(rl_level2, 100);
            closeLevel(rl_level3, 0);
        } else if (isLevel2Show) {//3. 如果第二层显示
            //第二层关闭
            closeLevel(rl_level2, 0);
        }
    }

    /**
     * @param rl_level
     * @param delayTime
     */
    private void closeLevel(RelativeLayout rl_level, long delayTime) {
        //动画没有播放完
        if (animCount != 0) { //动画不播放完成, 不进行下一步
            return;
        }

        //设置该层的组件不可用
        isEnable(rl_level, false);

        //记录关闭的状态
        LevelState(rl_level, false);
        RotateAnimation ra = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1f);
        ra.setDuration(500);//动画时间
        ra.setFillAfter(true);//停止在结束位置
        ra.setStartOffset(delayTime);//动画延迟时间
        ra.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animCount++;//动画开始标记
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animCount--;//动画结束的标记
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rl_level.startAnimation(ra);
    }

    /**
     * 判断3层的状态
     * @param rl_level
     * @param state
     */
    private void LevelState(RelativeLayout rl_level, boolean state) {
        if (rl_level.getId() == R.id.rl_level1) {
            isLevel1Show = state;
        } else if (rl_level.getId() == R.id.rl_level2) {
            isLevel2Show = state;
        } else if (rl_level.getId() == R.id.rl_level3) {
            isLevel3Show = state;
        }
    }

    /**
     * 设置组件是否可用
     *
     * @param rl_level
     * @param b        是否可用
     */
    private void isEnable(RelativeLayout rl_level, boolean b) {
        rl_level.setEnabled(b);//设置容器不可用
        //设置子控件不可用
        for (int i = 0; i < rl_level.getChildCount(); i++) {
            rl_level.getChildAt(i).setEnabled(b);
        }
    }


    /**
     * @param rl_level  传入的层
     * @param delayTime 时间延迟
     */
    private void openLevel(RelativeLayout rl_level, long delayTime) {
        if (animCount != 0) { //动画不播放完成, 不进行下一步
            return;
        }
        //判断动画是否播放完
        isEnable(rl_level, true);
        //记录打开的状态
        LevelState(rl_level, true);

        RotateAnimation ra = new RotateAnimation(-180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1f);
        ra.setDuration(500);//动画时间
        ra.setFillAfter(true);//停止在结束位置
        ra.setStartOffset(delayTime);//动画延迟时间
        ra.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animCount++;// 记录动画开始的标记
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animCount--;// 记录动画播放完毕
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rl_level.startAnimation(ra);
    }

    private void initView() {
        //获取焦点,才能使用手机的按键事件
        setFocusableInTouchMode(true);//触摸模式为true
        //
        View rooView = View.inflate(getContext(), R.layout.view_yu_ku, this);
        //菜单获取
        rl_level1 = (RelativeLayout) rooView.findViewById(R.id.rl_level1);

        rl_level2 = (RelativeLayout) rooView.findViewById(R.id.rl_level2);

        rl_level3 = (RelativeLayout) rooView.findViewById(R.id.rl_level3);

        iv_menu = (ImageView) rooView.findViewById(R.id.iv_menu);

        iv_home = (ImageView) rooView.findViewById(R.id.iv_home);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //处理硬件上的Menu菜单
        if (keyCode == KeyEvent.KEYCODE_MENU) { //82
                /*
         *  1. 都关闭，都显示
		 *  2. 都显示，都关闭
		 *  3. 显示那个level 就关闭哪些level
		 */

            if (isLevel1Show && isLevel2Show && isLevel3Show) {//都显示
                //都关闭
                closeLevel(rl_level3, 0);
                closeLevel(rl_level2, 100);
                closeLevel(rl_level1, 200);//延迟100毫秒
            } else if (!isLevel1Show && !isLevel2Show && !isLevel3Show) {//都关闭
                //都显示
                openLevel(rl_level1, 0);
                openLevel(rl_level2, 100);
                openLevel(rl_level3, 200);//延迟100毫秒
            } else if (isLevel1Show && isLevel2Show) {
                closeLevel(rl_level2, 0);
                closeLevel(rl_level1, 100);//延迟100毫秒
            } else if (isLevel1Show) {
                closeLevel(rl_level1, 0);
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
