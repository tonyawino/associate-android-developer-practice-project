package co.ke.tonyoa.android.associate_android_developer_practice_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.LayoutRecyclerBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Hour;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Skill;
import co.ke.tonyoa.android.associate_android_developer_practice_project.viewholders.MainRecyclerAdapter;
import co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels.HoursViewModel;
import co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels.LoadStateObserver;
import co.ke.tonyoa.android.associate_android_developer_practice_project.viewmodels.SkillsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    public enum ListType{
        HOURS,
        SKILLIQ
    }

    private static final String ARG_LIST_TYPE = "LIST_TYPE";

    private ListType mListType;
    private LayoutRecyclerBinding mLayoutRecyclerBinding;
    private HoursViewModel mHoursViewModel;
    private SkillsViewModel mSkillsViewModel;
    private MainRecyclerAdapter<Hour> mHourMainRecyclerAdapter;
    private MainRecyclerAdapter<Skill> mSkillMainRecyclerAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mListType = (ListType) getArguments().getSerializable(ARG_LIST_TYPE);
        }
        else
            mListType=ListType.SKILLIQ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutRecyclerBinding=LayoutRecyclerBinding.inflate(inflater, container, false);

        if (mListType==ListType.HOURS){
            mHoursViewModel=new ViewModelProvider(this).get(HoursViewModel.class);
            mHourMainRecyclerAdapter=new MainRecyclerAdapter<>(getContext(), Hour.HOUR_ITEM_CALLBACK, ListType.HOURS);
            mLayoutRecyclerBinding.recyclerViewRecycler.setAdapter(mHourMainRecyclerAdapter);
        }
        else {
            mSkillsViewModel=new ViewModelProvider(this).get(SkillsViewModel.class);
            mSkillMainRecyclerAdapter=new MainRecyclerAdapter<>(getContext(), Skill.SKILL_ITEM_CALLBACK, ListType.SKILLIQ);
            mLayoutRecyclerBinding.recyclerViewRecycler.setAdapter(mSkillMainRecyclerAdapter);
        }
        mLayoutRecyclerBinding.recyclerViewRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));

        setObservers();

        mLayoutRecyclerBinding.swipeRefreshLayoutRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        mLayoutRecyclerBinding.buttonRecyclerRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });

        return mLayoutRecyclerBinding.getRoot();
    }

    private void refresh() {
        if (mListType== ListType.HOURS){
            mHoursViewModel.getRefetch().setValue(true);
        }
        else {
            mSkillsViewModel.getRefetch().setValue(true);
        }
        fetch();
    }

    private void setObservers() {
        String type;
        if (mListType==ListType.HOURS){
            type="Hours";
        }
        else {
            type="Skills";
        }
        LoadStateObserver loadStateObserver = new LoadStateObserver(
                "No "+type+" found",
                "Unable to fetch "+type+", check your internet connection and try again",
                mLayoutRecyclerBinding.textViewRecyclerEmpty,
                mLayoutRecyclerBinding.recyclerViewRecycler, mLayoutRecyclerBinding.swipeRefreshLayoutRecycler,
                mLayoutRecyclerBinding.buttonRecyclerRetry);
        if (mListType==ListType.HOURS){
            mHoursViewModel.getLoadStateLiveData().observe(getViewLifecycleOwner(), loadStateObserver);
            mHoursViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Hour>>() {
                @Override
                public void onChanged(List<Hour> hours) {
                    mHourMainRecyclerAdapter.submitList(hours);
                    mHoursViewModel.updateLoadState();
                }
            });
        }
        else {
            mSkillsViewModel.getLoadStateLiveData().observe(getViewLifecycleOwner(), loadStateObserver);
            mSkillsViewModel.getListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Skill>>() {
                @Override
                public void onChanged(List<Skill> skills) {
                    mSkillMainRecyclerAdapter.submitList(skills);
                    mSkillsViewModel.updateLoadState();
                }
            });
        }
    }

    private void fetch(){
        Boolean refetch;
        if (mListType==ListType.HOURS){
            refetch=mHoursViewModel.getRefetch().getValue();
        }
        else {
            refetch=mSkillsViewModel.getRefetch().getValue();
        }
        if (refetch!=null && refetch){
            if (mListType==ListType.HOURS){
                mHoursViewModel.fetch();
            }
            else {
                mSkillsViewModel.fetch();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLayoutRecyclerBinding=null;
    }

    public static MainFragment newInstance(ListType listType) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIST_TYPE, listType);
        fragment.setArguments(args);
        return fragment;
    }
}