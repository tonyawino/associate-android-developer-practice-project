package co.ke.tonyoa.android.associate_android_developer_practice_project.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtils {
    private static GadsEndpoints mGadsEndpoints;
    private static GoogleFormEndpoints mGoogleFormEndpoints;

    private RetrofitUtils(){

    }

    public static GadsEndpoints getGadsEndpointsInstance(){
        if (mGadsEndpoints==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("https://gadsapi.herokuapp.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mGadsEndpoints=retrofit.create(GadsEndpoints.class);
        }
        return mGadsEndpoints;
    }

    public static GoogleFormEndpoints getGoogleFormEndpointsInstance(){
        if (mGoogleFormEndpoints==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("https://docs.google.com/forms/d/e/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            mGoogleFormEndpoints=retrofit.create(GoogleFormEndpoints.class);
        }
        return mGoogleFormEndpoints;
    }
}
