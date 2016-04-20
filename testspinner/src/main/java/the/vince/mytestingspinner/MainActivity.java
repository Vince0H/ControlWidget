package the.vince.mytestingspinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import the.vince.myspinnerlibrary.SpinnerView;

public class MainActivity extends AppCompatActivity {

    private SpinnerView mSpinnerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //绑定 sp View
        mSpinnerView = (SpinnerView) findViewById(R.id.sp_test);
        mSpinnerView.setOnSetDataListener(new SpinnerView.OnSetDataListener() {
            @Override
            public List<SpinnerView.SpinnerBean> getDatas() {
                List<SpinnerView.SpinnerBean> beans = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    SpinnerView.SpinnerBean bean = new SpinnerView.SpinnerBean();

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
