package co.ke.tonyoa.android.associate_android_developer_practice_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.ActivityMainBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.AppBarMainBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.ContentMainBinding;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding mActivityMainBinding;
    private AppBarMainBinding mAppBarMainBinding;
    private ContentMainBinding mContentMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityMainBinding.getRoot());
        mAppBarMainBinding=mActivityMainBinding.layoutMainAppBarMain;
        mContentMainBinding=mActivityMainBinding.layoutMainContentMain;

        mAppBarMainBinding.textViewMainSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SubmissionActivity.class);
                startActivity(intent);
            }
        });

        MainFragmentAdapter mainFragmentAdapter=new MainFragmentAdapter(this, getSupportFragmentManager());
        mContentMainBinding.viewPagerMain.setAdapter(mainFragmentAdapter);
        mContentMainBinding.tabLayoutMain.setupWithViewPager(mContentMainBinding.viewPagerMain);
    }

}