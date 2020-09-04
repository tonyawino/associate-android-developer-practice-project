package co.ke.tonyoa.android.associate_android_developer_practice_project.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import static co.ke.tonyoa.android.associate_android_developer_practice_project.Utils.areTheSame;
import static co.ke.tonyoa.android.associate_android_developer_practice_project.models.Hour.*;

@Entity(tableName = HOUR_TABLENAME,
        indices = {@Index(value = {NAME_COLUMN, HOURS_COLUMN, COUNTRY_COLUMN, BADGE_URL_COLUMN}, unique = true)})
public class Hour {
    public static final String HOUR_TABLENAME = "hour";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String HOURS_COLUMN = "hours";
    public static final String COUNTRY_COLUMN = "country";
    public static final String BADGE_URL_COLUMN = "badgeUrl";

    public static final DiffUtil.ItemCallback<Hour> HOUR_ITEM_CALLBACK=new DiffUtil.ItemCallback<Hour>() {
        @Override
        public boolean areItemsTheSame(@NonNull Hour oldItem, @NonNull Hour newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Hour oldItem, @NonNull Hour newItem) {
            return oldItem.equals(newItem);
        }
    };

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    private long mId;
    @ColumnInfo(name = NAME_COLUMN)
    @SerializedName(NAME_COLUMN)
    private String mName;
    @ColumnInfo(name = HOURS_COLUMN)
    @SerializedName(HOURS_COLUMN)
    private int mHours;
    @ColumnInfo(name = COUNTRY_COLUMN)
    @SerializedName(COUNTRY_COLUMN)
    private String mCountry;
    @ColumnInfo(name = BADGE_URL_COLUMN)
    @SerializedName(BADGE_URL_COLUMN)
    private String mBadgeUrl;

    public Hour(long id, String name, int hours, String country, String badgeUrl) {
        mId = id;
        mName = name;
        mHours = hours;
        mCountry = country;
        mBadgeUrl = badgeUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getHours() {
        return mHours;
    }

    public void setHours(int hours) {
        mHours = hours;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getBadgeUrl() {
        return mBadgeUrl;
    }

    public void setBadgeUrl(String badgeUrl) {
        mBadgeUrl = badgeUrl;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj==this)
            return true;
        if (obj instanceof Hour){
            Hour hour=(Hour)obj;
            return mId==hour.getId() &&
                    areTheSame(mName, hour.getName()) &&
                    mHours==hour.getHours() &&
                    areTheSame(mCountry, hour.getCountry()) &&
                    areTheSame(mBadgeUrl, hour.getBadgeUrl());
        }
        return false;
    }
}
