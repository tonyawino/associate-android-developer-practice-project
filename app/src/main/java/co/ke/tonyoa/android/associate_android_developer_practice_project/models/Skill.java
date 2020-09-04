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
import static co.ke.tonyoa.android.associate_android_developer_practice_project.models.Skill.*;

@Entity(tableName = SKILL_TABLENAME,
        indices = @Index(value = {NAME_COLUMN, SCORE_COLUMN, COUNTRY_COLUMN, BADGE_URL_COLUMN}, unique = true))
public class Skill {

    public static final String SKILL_TABLENAME = "skill";
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String SCORE_COLUMN = "score";
    public static final String COUNTRY_COLUMN = "country";
    public static final String BADGE_URL_COLUMN = "badgeUrl";

    public static final DiffUtil.ItemCallback<Skill> SKILL_ITEM_CALLBACK=new DiffUtil.ItemCallback<Skill>() {
        @Override
        public boolean areItemsTheSame(@NonNull Skill oldItem, @NonNull Skill newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Skill oldItem, @NonNull Skill newItem) {
            return oldItem.equals(newItem);
        }
    };

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_COLUMN)
    private long mId;
    @ColumnInfo(name = NAME_COLUMN)
    @SerializedName(NAME_COLUMN)
    private String mName;
    @ColumnInfo(name = SCORE_COLUMN)
    @SerializedName(SCORE_COLUMN)
    private int mScore;
    @ColumnInfo(name = COUNTRY_COLUMN)
    @SerializedName(COUNTRY_COLUMN)
    private String mCountry;
    @ColumnInfo(name = BADGE_URL_COLUMN)
    @SerializedName(BADGE_URL_COLUMN)
    private String mBadgeUrl;

    public Skill(long id, String name, int score, String country, String badgeUrl) {
        mId = id;
        mName = name;
        mScore = score;
        mCountry = country;
        mBadgeUrl = badgeUrl;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj==this)
            return true;
        if (obj instanceof Skill){
            Skill skill=(Skill) obj;
            return mId==skill.getId() &&
                    areTheSame(mName, skill.getName()) &&
                    mScore==skill.getScore() &&
                    areTheSame(mCountry, skill.getCountry()) &&
                    areTheSame(mBadgeUrl, skill.getBadgeUrl());
        }
        return false;
    }
}
