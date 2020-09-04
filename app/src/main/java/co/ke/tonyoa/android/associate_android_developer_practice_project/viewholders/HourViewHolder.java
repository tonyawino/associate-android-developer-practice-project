package co.ke.tonyoa.android.associate_android_developer_practice_project.viewholders;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import co.ke.tonyoa.android.associate_android_developer_practice_project.R;
import co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.ItemLeaderBinding;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Hour;

public class HourViewHolder extends BindableViewHolder<Hour> {

    @NonNull
    private final co.ke.tonyoa.android.associate_android_developer_practice_project.databinding.ItemLeaderBinding mItemLeaderBinding;
    private final Context mContext;

    public HourViewHolder(Context context, @NonNull ItemLeaderBinding itemLeaderBinding, Listable<Hour> listable) {
        super(itemLeaderBinding.getRoot(), listable);
        mItemLeaderBinding = itemLeaderBinding;
        mContext = context;
    }

    @Override
    public void bind(int position) {
        Hour hour=getListable().getList().get(position);
        Glide.with(mContext)
                .load(hour.getBadgeUrl())
                .placeholder(R.drawable.gads)
                .into(mItemLeaderBinding.imageViewItemLeader);
        mItemLeaderBinding.textViewItemLeaderName.setText(hour.getName());
        mItemLeaderBinding.textViewItemLeaderSkill.setText(mContext.getString(R.string.hours_display,
                hour.getHours(), hour.getCountry()));
    }
}
