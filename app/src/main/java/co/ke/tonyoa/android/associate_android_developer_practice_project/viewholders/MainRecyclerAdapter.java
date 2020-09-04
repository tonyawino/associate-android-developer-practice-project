package co.ke.tonyoa.android.associate_android_developer_practice_project.viewholders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.List;

import co.ke.tonyoa.android.associate_android_developer_practice_project.MainFragment;
import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.ItemLeaderBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Hour;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Skill;

public class MainRecyclerAdapter<T> extends ListAdapter<T, BindableViewHolder<T>> implements Listable<T> {

    private final Context mContext;
    private final MainFragment.ListType mListType;

    public MainRecyclerAdapter(Context context, @NonNull DiffUtil.ItemCallback<T> diffCallback, MainFragment.ListType listType) {
        super(diffCallback);
        mContext = context;
        mListType = listType;
    }

    @NonNull
    @Override
    public BindableViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLeaderBinding itemLeaderBinding=ItemLeaderBinding.inflate(LayoutInflater.from(mContext),
                parent, false);
        switch (mListType){
            case HOURS:
                return (BindableViewHolder<T>) new HourViewHolder(mContext, itemLeaderBinding,
                        (Listable<Hour>) this);
            case SKILLIQ:
                return (BindableViewHolder<T>) new SkillViewHolder(mContext, itemLeaderBinding,
                        (Listable<Skill>) this);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BindableViewHolder<T> holder, int position) {
        holder.bind(position);
    }

    @Override
    public List<T> getList() {
        return getCurrentList();
    }
}
