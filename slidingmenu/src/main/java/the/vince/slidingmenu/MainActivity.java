package the.vince.slidingmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends Activity {

    private SlidingMenuView mSmv_test;
    private TextView mTv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.slidingmunu_layout);

        mSmv_test = (SlidingMenuView) findViewById(R.id.smv_test);
        mTv_content = (TextView) findViewById(R.id.tv_content);

    }

    public void back(View view){

        //开打左侧菜单
        mSmv_test.leftMenuToggle();
    }

    public void showContent(View v){
        TextView tv = (TextView) v;
        //System.out.println("单击事件" + tv.getText());
        mTv_content.setText(tv.getText() + "的内容");

        //关闭左侧菜单
       mSmv_test.closeLeftMenu();
    }

}
