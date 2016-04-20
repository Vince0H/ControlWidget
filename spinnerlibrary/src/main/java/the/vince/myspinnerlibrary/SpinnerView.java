package the.vince.myspinnerlibrary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author Vince
 * @time 2016/4/19 23:28
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public class SpinnerView extends RelativeLayout {

    private EditText mEt_number;
    private ImageView mIv_arrow;
    private ListView mContentView;
    private PopupWindow pw_numbers;

    private List<SpinnerBean> mDatas;
    private MyAdapter myAdapter;

    private OnSetDataListener onSetDataListener;

    /**
     * 自定义
     * 数据 bean
     */
    public static class SpinnerBean {
        public int icon_id;
        public Drawable icon_drawable;
        public String number;
    }

    public interface OnSetDataListener {
        List<SpinnerBean> getDatas();
    }

    public void setOnSetDataListener(OnSetDataListener listener) {
        this.onSetDataListener = listener;
        //监听回调 让使用者设置适配的数据
        mDatas = listener.getDatas();
    }

    public void setDatas(List<SpinnerBean> datas) {
        this.mDatas = datas;
        myAdapter.notifyDataSetChanged();
    }

    public SpinnerView(Context context) {

        super(context);

    }

    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initPopupWindow();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        //监听添加单击事件
        mIv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出窗体显示号码列表
                showPW();
            }
        });

        //给listView添加item点击事件
        mContentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击条目
                //1. 让et显示选中的数据
                SpinnerBean spinnerBean = mDatas.get(position);
                mEt_number.setText(spinnerBean.number);
                //2. 关闭 PW
                showPW();
            }
        });
    }

    private void showPW() {
        if (pw_numbers != null && pw_numbers.isShowing()) {
            //如果显示, 则关闭
        } else {
            //如果关闭,则显示
            //弹出窗体在et的下方
            //设置宽度
            //获取et宽度
            //测量


            //获取测量后的宽度
            pw_numbers.setWidth(mEt_number.getWidth());
            pw_numbers.showAsDropDown(mEt_number);
        }
    }

    /**
     * 初始化弹出窗体
     */
    private void initPopupWindow() {
        //listview 来显示号码
        // ActionBar.LayoutParams.WRAP_CONTENT
        mContentView = new ListView(getContext());
        //添加listView 的背景
        mContentView.setBackgroundResource(R.drawable.item_frame);
        myAdapter = new MyAdapter();
        mContentView.setAdapter(myAdapter);

        // -2 包裹内容
        pw_numbers = new PopupWindow(mContentView, -2, 180);

        pw_numbers.setFocusable(true);// 获取焦点

        //设置透明背景
        pw_numbers.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw_numbers.setOutsideTouchable(true); // 外部点击关闭

    }

    private void initView() {
       // setContentView(R.layout.view_spinner);
        //获取焦点,才能使用手机的按键事件
        setFocusableInTouchMode(true);//触摸模式为true
        View rootView = View.inflate(getContext(),R.layout.view_spinner,this);
        mEt_number = (EditText) rootView.findViewById(R.id.et_number);
        mIv_arrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mDatas == null) {
                return 0;
            }
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, android.view.View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            //缓存复用
            if (convertView == null) {
                //没有缓存服用
                convertView = View.inflate(getContext(), R.layout.item_lv, null);
                viewHolder = new ViewHolder();

                //设置listView
                viewHolder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
                viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                viewHolder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
                convertView.setTag(viewHolder);
            } else {

                viewHolder = (ViewHolder) convertView.getTag();
            }
            //组件赋值
            final SpinnerBean bean = mDatas.get(position);

            viewHolder.tv_number.setText(bean.number);
            //头像
            if (bean.icon_id != 0) {
                //id 设置头像
                viewHolder.iv_user.setImageResource(bean.icon_id);
            } else if (bean.icon_drawable != null) {
                //drawable
                viewHolder.iv_user.setImageDrawable(bean.icon_drawable);
            }
            //删除数据
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除容器中的数据
                    mDatas.remove(bean);
                    //更新界面
                    myAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }


    private class ViewHolder {
        ImageView iv_user;
        TextView tv_number;
        ImageView iv_delete;
    }
}
