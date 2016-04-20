package the.vince.spinnerdemo;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Vince
 * @time 2016/4/19 23:41
 * @updateAuthor $Author$
 * @updateDate $Date$
 */
public class TestSpinner extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        /**
         * 测试代码
         *
         * */
        setOnSetDataListener(new OnSetDataListener() {
            @Override
            public List<SpinnerBean> getDatas() {
                List<SpinnerBean> beans = new ArrayList<SpinnerBean>();
                for (int i = 0; i < 100; i++) {
                    SpinnerBean bean = new SpinnerBean();

                    //号码
                    bean.number = "300" + i;

                    //动态设置头像
                    if (i % 2 == 0) {
                        bean.icon_id = R.mipmap.ic_launcher;

                    } else {
                        bean.icon_drawable = getResources().getDrawable(R.drawable.user);

                    }

                    beans.add(bean);
                }
                return beans;
            }

        });
    }
}
