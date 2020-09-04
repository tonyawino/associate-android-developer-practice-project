package co.ke.tonyoa.android.associate_android_developer_practice_project.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Hour;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Skill;

@Database(entities = {Hour.class, Skill.class}, version = 1)
public abstract class RoomUtils extends RoomDatabase {
    private static final String DATABASE_NAME="AAD.db";
    private static RoomUtils sRoomUtils;

    public synchronized static RoomUtils getInstance(Context context){
        if (sRoomUtils==null){
            sRoomUtils= Room.databaseBuilder(context, RoomUtils.class, DATABASE_NAME).build();
        }
        return sRoomUtils;
    }

    public abstract ProjectDao getProjectDao();

}
