package com.kukuhsain.simpletour.admin.model.remote;

import com.google.gson.JsonObject;
import com.kukuhsain.simpletour.admin.model.local.PreferencesHelper;
import com.kukuhsain.simpletour.admin.model.pojo.Destination;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by kukuh on 15/10/16.
 */

public class SimpleTourApi {
    public static final String BASE_URL = "http://simple-tour.appspot.com";
    private static SimpleTourApi INSTANCE;
    private ApiEndpoint api;
    private static String accessToken;

    private SimpleTourApi() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();
        api = retrofit.create(ApiEndpoint.class);
    }

    public static SimpleTourApi getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SimpleTourApi();
        }
        accessToken = PreferencesHelper.getInstance().getAccessToken();
        return INSTANCE;
    }

    public Observable<String> signIn(String email, String password) {
        return api.signIn(email, password).map(jsonObject -> {
            String accessToken = jsonObject.get("accessToken").getAsString();
            return accessToken;
        });
    }

    public Observable<List<Destination>> getDestinations() {
        return api.getDestinations();
    }

    public Observable<Destination> addDestination(String title, String content, String location, String imagePath) {
        RequestBody rbAccessToken = RequestBody.create(MediaType.parse("text/plain"), accessToken);
        RequestBody rbTitle = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody rbContent = RequestBody.create(MediaType.parse("text/plain"), content);
        RequestBody rbLocation = RequestBody.create(MediaType.parse("text/plain"), location);
        File imageFile = new File(imagePath);
        RequestBody rbImage = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", imageFile.getName(), rbImage);
        return api.addDestination(rbAccessToken, rbTitle, rbContent, rbLocation, image);
    }

    private interface ApiEndpoint {
        @FormUrlEncoded
        @POST("/admin/login")
        Observable<JsonObject> signIn(@Field("email") String email,
                                      @Field("password") String password);

        @GET("/destinations")
        Observable<List<Destination>> getDestinations();

        @Multipart
        @POST("/destination/add")
        Observable<Destination> addDestination(@Part("access_token") RequestBody accessToken,
                                               @Part("title") RequestBody title,
                                               @Part("content") RequestBody content,
                                               @Part("location") RequestBody location,
                                               @Part MultipartBody.Part image);
    }
}
