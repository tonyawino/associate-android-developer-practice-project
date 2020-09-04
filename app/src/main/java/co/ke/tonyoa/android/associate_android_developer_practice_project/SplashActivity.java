package co.ke.tonyoa.android.associate_android_developer_practice_project;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.Executors;

import co.ke.tonyoa.android.associate_android_developer_practice_project.data.ProjectRepo;
import co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels.LoadState;

import static co.ke.tonyoa.android.associate_android_developer_practice_project.Utils.isConnected;

public class SplashActivity extends AppCompatActivity {

    private boolean mLoadedSkills;
    private boolean mLoadedHours;
    private Handler mHandler;
    private Runnable mFinishSplashActivityRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        mFinishSplashActivityRunnable = this::finishSplashActivity;
        //If has internet connection, fetch items for up to 10 seconds, if no internet, show splash for 3.5 seconds
        if (isConnected(this)) {
            ProjectRepo projectRepo = new ProjectRepo(this);
            Executors.newSingleThreadExecutor().execute(()->{
                projectRepo.fetchSkillsOnline();
                projectRepo.fetchHoursOnline();
            });

            projectRepo.getLoadStateHoursMutableLiveData().observe(this, new Observer<LoadState>() {
                @Override
                public void onChanged(LoadState loadState) {
                    if (loadState.getLoadStateType()!= LoadState.LoadStateType.LOADING) {
                        mLoadedHours = true;
                        finishLoading();
                    }
                }
            });
            projectRepo.getLoadStateSkillsMutableLiveData().observe(this, new Observer<LoadState>() {
                @Override
                public void onChanged(LoadState loadState) {
                    if (loadState.getLoadStateType()!= LoadState.LoadStateType.LOADING) {
                        mLoadedSkills = true;
                        finishLoading();
                    }
                }
            });
            mHandler.postDelayed(mFinishSplashActivityRunnable, 10000);
        }
        else {
            mHandler.postDelayed(mFinishSplashActivityRunnable, 3500);
        }
    }

    private void finishLoading() {
        if (mLoadedHours && mLoadedSkills){
            finishSplashActivity();
            mHandler.removeCallbacks(mFinishSplashActivityRunnable);
        }
    }

    private void finishSplashActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}