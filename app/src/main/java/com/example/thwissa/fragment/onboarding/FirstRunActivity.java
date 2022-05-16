package com.example.thwissa.fragment.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.thwissa.MainActivity;
import com.example.thwissa.R;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;




public class FirstRunActivity extends AppCompatActivity {
    public static final String key_isFinishClicked = "isFinishClicked";
    private Button button;
    private ViewPager viewPager;
    private VP_Adapter vp_adapter;
    private WormDotsIndicator wormDotsIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run2);

        SharedPreferences sharedPreferences =getSharedPreferences("first_run_check", MODE_PRIVATE);

        boolean isChecked = sharedPreferences.getBoolean(
                key_isFinishClicked, false
        );

        if(isChecked){
            finish();
        }else{
            button = findViewById(R.id.skip_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(key_isFinishClicked, true);
                    editor.apply();

                    finish();
                }
            });
        }

        wormDotsIndicator = findViewById(R.id.dots_indicator);

        viewPager = findViewById(R.id.view_pager);
        vp_adapter = new VP_Adapter(this);
        viewPager.setAdapter(vp_adapter);

        wormDotsIndicator.setViewPager(viewPager);
    }

    @Override
    public void finish() {
        navigate_to_main_activity();
        super.finish();
    }

    private void navigate_to_main_activity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}