package co.ke.tonyoa.android.associate_android_developer_practice_project.data;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Hour;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Skill;

@Dao
public interface ProjectDao {

    //Hour operations
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertHours(Hour... hours);
    @Update(onConflict = OnConflictStrategy.IGNORE)
    int updateHours(Hour... hours);
    @Delete
    int deleteHours(Hour... hours);
    @Query("SELECT * FROM hour")
    List<Hour> getAllHours();
    @Query("SELECT * FROM hour")
    LiveData<List<Hour>> getAllHoursLive();

    //Skill operations
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertSkills(Skill... skills);
    @Update(onConflict = OnConflictStrategy.IGNORE)
    int updateSkills(Skill... skills);
    @Delete
    int deleteSkills(Skill... skills);
    @Query("SELECT * FROM skill")
    List<Skill> getAllSkills();
    @Query("SELECT * FROM skill")
    LiveData<List<Skill>> getAllSkillsLive();
}
