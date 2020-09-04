package co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import co.ke.tonyoa.android.associate_android_developer_practice_project.data.ProjectRepo;

public abstract class MainFragmentViewModel extends AndroidViewModel {

    protected MutableLiveData<LoadState> mLoadStateLiveData;
    private MutableLiveData<Boolean> mRefetch=new MutableLiveData<>();
    private ProjectRepo mProjectRepo;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        mProjectRepo=new ProjectRepo(application);
        mRefetch.postValue(false);
    }

    public LiveData<LoadState> getLoadStateLiveData() {
        return mLoadStateLiveData;
    }

    //Call updateLoadState to trigger notice change in recycler items
    public void updateLoadState(){
        mLoadStateLiveData.setValue(mLoadStateLiveData.getValue());
    }

    public MutableLiveData<Boolean> getRefetch() {
        return mRefetch;
    }

    protected ProjectRepo getProjectRepo() {
        return mProjectRepo;
    }

    public abstract void fetch();

    public abstract<T> LiveData<List<T>> getListLiveData();
}
