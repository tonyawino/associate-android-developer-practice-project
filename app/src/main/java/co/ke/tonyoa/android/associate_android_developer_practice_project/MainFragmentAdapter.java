package co.ke.tonyoa.android.associate_android_developer_practice_project;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MainFragmentAdapter extends FragmentStatePagerAdapter {

    private final Context mContext;
    private MainFragment mMainFragmentHours;
    private MainFragment mMainFragmentSkills;

    public MainFragmentAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        mMainFragmentHours=MainFragment.newInstance(MainFragment.ListType.HOURS);
        mMainFragmentSkills=MainFragment.newInstance(MainFragment.ListType.SKILLIQ);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mMainFragmentHours;
            case 1:
                return mMainFragmentSkills;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.learning_leaders);
            case 1:
                return mContext.getString(R.string.skill_iq_leaders);
        }
        return super.getPageTitle(position);
    }
}
