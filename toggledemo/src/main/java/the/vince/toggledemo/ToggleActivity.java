package the.vince.toggledemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import the.vince.togglelibrary.ToggleView;

public class ToggleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);

        ToggleView toggleView = (ToggleView) findViewById(R.id.toggle_test);
        toggleView.setOnToggleChangeListener(new ToggleView.OnToggleChangeListener() {
            @Override
            public void onToggleChanged(ToggleView toggleView, boolean isToggleOn) {

                if (isToggleOn) {
                    Toast.makeText(getApplication(), "打开", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "close", Toast.LENGTH_SHORT).show();

                }
            }

        });
        }
    }
