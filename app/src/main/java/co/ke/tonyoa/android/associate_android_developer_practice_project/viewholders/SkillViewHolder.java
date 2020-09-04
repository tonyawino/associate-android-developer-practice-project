package co.ke.tonyoa.android.associate_android_developer_practice_project.viewholders;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import co.ke.tonyoa.android.associate_android_developer_practice_project.R;
import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.ItemLeaderBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Hour;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Skill;

public class SkillViewHolder extends BindableViewHolder<Skill> {

    private final Context mContext;
    @NonNull
    private final co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.ItemLeaderBinding mItemLeaderBinding;

    public SkillViewHolder(Context context, @NonNull ItemLeaderBinding itemLeaderBinding, Listable<Skill> listable) {
        super(itemLeaderBinding.getRoot(), listable);
        mContext = context;
        mItemLeaderBinding = itemLeaderBinding;
    }

    @Override
    public void bind(int position) {
        Skill skill=getListable().getList().get(position);
        Glide.with(mContext)
                .load(skill.getBadgeUrl())
                .placeholder(R.drawable.gads)
                .into(mItemLeaderBinding.imageViewItemLeader);
        mItemLeaderBinding.textViewItemLeaderName.setText(skill.getName());
        mItemLeaderBinding.textViewItemLeaderSkill.setText(mContext.getString(R.string.skills_display,
                skill.getScore(), skill.getCountry()));
    }
}
