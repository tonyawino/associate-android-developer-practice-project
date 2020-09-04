package co.ke.tonyoa.android.associate_android_developer_practice_project.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GoogleFormEndpoints {

    String emailField="entry.1824927963";
    String firstNameField="entry.1877115667";
    String lastNameField="entry.2006916086";
    String linkToProjectField="entry.284483984";
    String trackField="entry.642603327";

    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    Call<String> submitForm(@Field(emailField) String email, @Field(firstNameField) String firstName,
                                  @Field(lastNameField) String lastName, @Field(linkToProjectField) String linkToProject,
                                  @Field(trackField) String track);
}
