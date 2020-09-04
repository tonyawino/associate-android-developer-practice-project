package co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executors;

import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Skill;

public class SkillsViewModel extends MainFragmentViewModel {

    private LiveData<List<Skill>> mSkillsLiveData;

    public SkillsViewModel(@NonNull Application application) {
        super(application);
        mSkillsLiveData=getProjectRepo().getAllSkillsLive();
        mLoadStateLiveData=getProjectRepo().getLoadStateSkillsMutableLiveData();
        fetch();
    }

    @Override
    public void fetch() {
        Executors.newSingleThreadExecutor().execute(()->{
            getProjectRepo().fetchSkillsOnline();
        });
        getRefetch().postValue(false);
    }

    @Override
    public LiveData<List<Skill>> getListLiveData() {
        return mSkillsLiveData;
    }
}
