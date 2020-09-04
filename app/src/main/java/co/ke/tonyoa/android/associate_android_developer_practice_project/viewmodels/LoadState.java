package co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels;

public class LoadState {
    public enum LoadStateType {
        LOADING,
        LOADED,
        ERROR
    }

    private LoadStateType mLoadStateType;
    private Object mResult;

    public LoadState(LoadStateType loadStateType, Object result){
        mLoadStateType = loadStateType;
        mResult = result;
    }

    public LoadStateType getLoadStateType() {
        return mLoadStateType;
    }

    public void setLoadStateType(LoadStateType loadStateType) {
        mLoadStateType = loadStateType;
    }

    public Object getResult() {
        return mResult;
    }

    public void setResult(Object result) {
        mResult = result;
    }

}
