package co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import static co.ke.tonyoa.android.associate_android_developer_practice_project.Utils.isNotNull;

public class LoadStateObserver implements Observer<LoadState> {

    private String mEmptyMessage;
    private String mErrorMessage;
    private TextView mTextViewEmpty;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button mButtonAdd;

    public LoadStateObserver(String emptyMessage, String errorMessage, TextView textViewEmpty,
                             RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout,
                             Button buttonAdd) {
        mEmptyMessage = emptyMessage;
        mErrorMessage = errorMessage;
        mTextViewEmpty = textViewEmpty;
        mRecyclerView = recyclerView;
        mSwipeRefreshLayout = swipeRefreshLayout;
        mButtonAdd = buttonAdd;
    }

    @Override
    public void onChanged(LoadState loadState) {
        refreshLayoutState(loadState);
        textViewState(loadState);
        recyclerViewState(loadState);
    }

    private void refreshLayoutState(LoadState loadState){
        if (isNotNull(mSwipeRefreshLayout)) {
            switch (loadState.getLoadStateType()) {
                case LOADING:
                    mSwipeRefreshLayout.setRefreshing(true);
                    break;
                case LOADED:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case ERROR:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    }

    private void textViewState(LoadState loadState){
        if (isNotNull(mTextViewEmpty)) {
            switch (loadState.getLoadStateType()) {
                case LOADING:
                    mTextViewEmpty.setVisibility(View.GONE);
                    if (isNotNull(mButtonAdd)){
                        mButtonAdd.setVisibility(View.GONE);
                    }
                    break;
                case LOADED:
                    //If the passed object is null, the list is empty
                    if (loadState.getResult()==null){
                        mTextViewEmpty.setVisibility(View.VISIBLE);
                        mTextViewEmpty.setText(mEmptyMessage);
                        if (isNotNull(mButtonAdd)){
                            mButtonAdd.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        mTextViewEmpty.setVisibility(View.GONE);
                        if (isNotNull(mButtonAdd)){
                            mButtonAdd.setVisibility(View.GONE);
                        }
                    }
                    break;
                case ERROR:
                    int count=-1;
                    RecyclerView.Adapter adapter=mRecyclerView.getAdapter();
                    if (adapter!=null){
                        count=adapter.getItemCount();
                    }
                    //If an error occurred and there are no items, display the error, otherwise display cached items
                    String errorMessage=mErrorMessage;
                    if (loadState.getResult() instanceof String){
                        errorMessage=(String)loadState.getResult();
                    }
                    mTextViewEmpty.setText(errorMessage);
                    if (count>0){
                        mTextViewEmpty.setVisibility(View.GONE);
                        if (isNotNull(mButtonAdd)){
                            mButtonAdd.setVisibility(View.GONE);
                        }
                    }
                    else {
                        mTextViewEmpty.setVisibility(View.VISIBLE);
                        if (isNotNull(mButtonAdd)){
                            mButtonAdd.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
            }
        }
    }

    private void recyclerViewState(LoadState loadState){
        if (isNotNull(mRecyclerView)) {
            switch (loadState.getLoadStateType()) {
                case LOADING:
                    mRecyclerView.setVisibility(View.VISIBLE);
                    break;
                case LOADED:
                    //If the passed object is null, the list is empty
                    if (loadState.getResult()==null){
                        mRecyclerView.setVisibility(View.GONE);
                    }
                    else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                    break;
                case ERROR:
                    int count=-1;
                    RecyclerView.Adapter adapter=mRecyclerView.getAdapter();
                    if (adapter!=null){
                        count=adapter.getItemCount();
                    }
                    //If an error occurred and there are no items, display the error, otherwise display cached items
                    if (count>0){
                        mRecyclerView.setVisibility(View.VISIBLE);
                    }
                    else {
                        mRecyclerView.setVisibility(View.GONE);
                    }
                    Snackbar.make(mRecyclerView, "Check your internet connection and try again",
                            BaseTransientBottomBar.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
