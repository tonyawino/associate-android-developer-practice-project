package co.ke.tonyoa.android.associate_android_developer_practice_project.data;

import java.util.List;

import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Hour;
import co.ke.tonyoa.android.associate_android_developer_practice_project.models.Skill;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GadsEndpoints {

    @GET(" /api/hours")
    Call<List<Hour>> getHours();
    @GET(" /api/skilliq")
    Call<List<Skill>> getSkills();
}
