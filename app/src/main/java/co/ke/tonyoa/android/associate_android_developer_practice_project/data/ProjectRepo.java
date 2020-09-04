package co.ke.tonyoa.android.associate_android_developer_practice_project.data;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Hour;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Skill;
import co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels.LoadState;
import okhttp3.ResponseBody;

import static co.ke.tonyoa.android.associate_android_developer_practice_project.Utils.isConnected;

public class ProjectRepo {

    private MutableLiveData<LoadState> mLoadStateHoursMutableLiveData =new MutableLiveData<>();
    private MutableLiveData<LoadState> mLoadStateSkillsMutableLiveData =new MutableLiveData<>();

    private ProjectDao mProjectDao;
    private Context mContext;
    private GadsEndpoints mGadsEndpoints;
    private GoogleFormEndpoints mGoogleFormEndpoints;

    public ProjectRepo(Context context){
        mProjectDao=RoomUtils.getInstance(context).getProjectDao();
        mContext = context;
        mGadsEndpoints=RetrofitUtils.getGadsEndpointsInstance();
        mGoogleFormEndpoints=RetrofitUtils.getGoogleFormEndpointsInstance();
    }

    public LiveData<List<Hour>> getAllHoursLive(){
        return mProjectDao.getAllHoursLive();
    }

    public void fetchHoursOnline() {
        setLoadingHours();
        if (isConnected(mContext)){
            try {
                List<Hour> hours=mGadsEndpoints.getHours().execute().body();
                if (hours!=null && hours.size()>0) {
                    mProjectDao.insertHours(hours.toArray(new Hour[0]));
                }
                setLoadedHours(hours);
            } catch (IOException e) {
                setErrorHours("Unable to fetch the data");
                e.printStackTrace();
            }
        }
        else {
            setErrorHours("Please check your internet connection and try again");
        }
    }

    public LiveData<List<Skill>> getAllSkillsLive(){
        return mProjectDao.getAllSkillsLive();
    }

    public void fetchSkillsOnline() {
        setLoadingSkills();
        if (isConnected(mContext)){
            try {
                List<Skill> skills=mGadsEndpoints.getSkills().execute().body();
                if (skills!=null && skills.size()>0) {
                    mProjectDao.insertSkills(skills.toArray(new Skill[0]));
                }
                setLoadedSkills(skills);
            } catch (IOException e) {
                setErrorSkills("Unable to fetch the data");
                e.printStackTrace();
            }
        }
        else {
            setErrorSkills("Please check your internet connection and try again");
        }
    }

    private void setLoadedHours(List items) {
        mLoadStateHoursMutableLiveData.postValue(getLoadState(items));
    }

    private void setErrorHours(String errorMessage) {
        LoadState loadState=new LoadState(LoadState.LoadStateType.ERROR, errorMessage);
        mLoadStateHoursMutableLiveData.postValue(loadState);
    }

    @NotNull
    private LoadState setLoadingHours() {
        LoadState loadState = new LoadState(LoadState.LoadStateType.LOADING, null);
        mLoadStateHoursMutableLiveData.postValue(loadState);
        return loadState;
    }

    private void setLoadedSkills(List items) {
        mLoadStateSkillsMutableLiveData.postValue(getLoadState(items));
    }

    private void setErrorSkills(String errorMessage) {
        LoadState loadState=new LoadState(LoadState.LoadStateType.ERROR, errorMessage);
        mLoadStateSkillsMutableLiveData.postValue(loadState);
    }

    @NotNull
    private LoadState setLoadingSkills() {
        LoadState loadState = new LoadState(LoadState.LoadStateType.LOADING, null);
        mLoadStateSkillsMutableLiveData.postValue(loadState);
        return loadState;
    }

    public boolean submitProject(@NonNull String email, @NonNull String firstName, @NonNull String lastName,
                                 @NonNull String linkToProject){
        String androidTrack = "Android";
        if (isConnected(mContext)) {
            try {
                String submitted = mGoogleFormEndpoints.submitForm(email, firstName, lastName,
                        linkToProject, androidTrack)
                        .execute().body();
                if (submitted==null)
                    return false;
                //A successful entry produces a webpage containing "Your response has been recorded."
                return submitted.contains("Your response has been recorded.");
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        else {
            return false;
        }
    }

    private LoadState getLoadState(List items) {
        String size;
        if (items==null || items.size()==0)
            size=null;
        else
            size="";
        return new LoadState(LoadState.LoadStateType.LOADED, size);
    }

    public MutableLiveData<LoadState> getLoadStateHoursMutableLiveData() {
        return mLoadStateHoursMutableLiveData;
    }


    public MutableLiveData<LoadState> getLoadStateSkillsMutableLiveData() {
        return mLoadStateSkillsMutableLiveData;
    }


}
