package co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executors;

import co.ke.tonyoa.android.associate_android_developer_practice_project.data.ProjectRepo;

public class SubmissionActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mLoadingMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<Boolean> mSuccessMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<Boolean> mPerformPost=new MutableLiveData<>();
    private ProjectRepo mProjectRepo;

    public SubmissionActivityViewModel(@NonNull Application application) {
        super(application);
        mProjectRepo=new ProjectRepo(application);
    }

    public void submitForm(String email, String firstName, String lastName, String githubLink){
        getPerformPost().setValue(true);
        Executors.newSingleThreadExecutor().execute(()->{
            mLoadingMutableLiveData.postValue(true);
            boolean success=mProjectRepo.submitProject(email, firstName, lastName, githubLink);
            mSuccessMutableLiveData.postValue(success);
            mLoadingMutableLiveData.postValue(false);
            getPerformPost().postValue(false);
        });
    }

    public LiveData<Boolean> getLoadingMutableLiveData() {
        return mLoadingMutableLiveData;
    }

    public MutableLiveData<Boolean> getSuccessMutableLiveData() {
        return mSuccessMutableLiveData;
    }

    public MutableLiveData<Boolean> getPerformPost() {
        return mPerformPost;
    }
}
