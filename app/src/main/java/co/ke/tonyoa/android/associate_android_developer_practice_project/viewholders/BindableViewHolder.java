package co.ke.tonyoa.android.associate_android_developer_practice_project.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BindableViewHolder<T> extends RecyclerView.ViewHolder {

    private final Listable<T> mListable;

    public BindableViewHolder(@NonNull View itemView, Listable<T> listable) {
        super(itemView);
        mListable = listable;
    }

    public abstract void bind(int position);

    public Listable<T> getListable() {
        return mListable;
    }
}
