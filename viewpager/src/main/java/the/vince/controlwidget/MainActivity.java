package the.vince.controlwidget;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp_test;
    private TextView tv_desc;
    private LinearLayout ll_points;
    //ViewPager 的数据容器
    List<ImageView> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化界面
        initView();
        //初始化数据
        initData();
        //初始化事件
        initEvent();
    }

    private void initEvent() {
        //TODO 创建事件
        //ViewPager 的活动事件
        vp_test.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // poisiton 页面选中的位置回调
                //设置点的显示 和 图片的描述
                selectPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        //TODO 创建数据
        // 1. 创建数据
        for (int i = 0; i < 5; i++) {
            // 1. 创建ImageView
            ImageView iv = new ImageView(this);

            // 2. 给iv设置数据
            switch (i) {
                case 0:
                    iv.setBackgroundResource(R.drawable.a);
                    break;
                case 1:
                    iv.setBackgroundResource(R.drawable.b);
                    break;
                case 2:
                    iv.setBackgroundResource(R.drawable.c);
                    break;
                case 3:
                    iv.setBackgroundResource(R.drawable.d);
                    break;
                case 4:
                    iv.setBackgroundResource(R.drawable.e);
                    break;
                default:
                    break;
            }


            // 3. 添加到容器中
            datas.add(iv);

            // 添加点
            View v_point = new View(this);
            // 默认是白点
            v_point.setBackgroundResource(R.drawable.point_select);
            // 设置大小 10pix dip
            LayoutParams lp = new LayoutParams(10, 10);
            // 设置点之间的间距
            lp.leftMargin = 7;

            v_point.setLayoutParams(lp);
            v_point.setEnabled(false);//状态选择器显示白点

            // 添加到点的容器中
            ll_points.addView(v_point);

        }

        // 2. 设置适配器给ViewPager
        MyAdapter adapter = new MyAdapter();
        vp_test.setAdapter(adapter);

        //3. 设置初始化位置
        int startPosition = 13;//1000000bug
        //默认选择的位置 应该0
        selectPosition(startPosition - startPosition % datas.size());

        //设置ViewPager选择的位置
        vp_test.setCurrentItem(startPosition - startPosition % datas.size());
    }


    private void selectPosition(int index) {
        //求模运算
        index = index % datas.size();
        // 设置描述信息
        tv_desc.setText("图片" + index + "的描述信息");

        // 点的显示为红点
        for (int i = 0; i < 5; i++) {
            //改变点的状态选择器
            ll_points.getChildAt(i).setEnabled(index == i);
        }
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        /**
         * @param view   缓存的View object View 的标记
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            //判断view是否展示
            return view == object;
        }

        /**
         * @param container 容器 ViewPager 就是vp_test
         * @param position  显示View的位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //1 . 初始化数据View

            //2. 把iv 添加到ViewPager 中 (ViewGroup的子类)
            ImageView iv = datas.get(position % datas.size());
            //3. 返回View标记
            vp_test.addView(iv);
            return iv;
        }

        /**
         * @param container
         * @param position
         * @param object    标记
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //回收view的回调
            //回收清理
            container.removeView((View) object);
        }
    }

    private void initView() {
        //获取ViewPager
        vp_test = (ViewPager) findViewById(R.id.vp_test);

        //获取信息
        tv_desc = (TextView) findViewById(R.id.tv_desc);

        //获取点容器
        ll_points = (LinearLayout) findViewById(R.id.ll_points);
    }
}
