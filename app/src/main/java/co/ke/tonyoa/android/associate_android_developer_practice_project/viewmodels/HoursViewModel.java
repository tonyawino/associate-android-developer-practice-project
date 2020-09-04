package co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executors;

import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Hour;

public class HoursViewModel extends MainFragmentViewModel {

    private LiveData<List<Hour>> mHoursLiveData;

    public HoursViewModel(@NonNull Application application) {
        super(application);
        mHoursLiveData=getProjectRepo().getAllHoursLive();
        mLoadStateLiveData=getProjectRepo().getLoadStateHoursMutableLiveData();
        fetch();
    }

    @Override
    public void fetch() {
        Executors.newSingleThreadExecutor().execute(()->{
            getProjectRepo().fetchHoursOnline();
        });
        getRefetch().postValue(false);
    }

    @Override
    public LiveData<List<Hour>> getListLiveData() {
        return mHoursLiveData;
    }

}
