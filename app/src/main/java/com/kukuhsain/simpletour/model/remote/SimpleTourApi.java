package com.kukuhsain.simpletour.model.remote;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kukuhsain.simpletour.model.pojo.Destination;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by kukuh on 15/10/16.
 */

public class SimpleTourApi {
    public static final String BASE_URL = "http://simple-tour.appspot.com";
    private static SimpleTourApi INSTANCE;
    private static ApiEndpoint api;

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
        return INSTANCE;
    }

    public Observable<List<Destination>> getDestinations() {
        return api.getDestinations().map(new Func1<JsonObject, List<Destination>>() {
            @Override
            public List<Destination> call(JsonObject jsonObject) {
                jsonObject.get("destinations").getAsJsonArray();
                Type listType = new TypeToken<List<Destination>>() {}.getType();
                List<Destination> destinations = new Gson().fromJson(jsonObject.get("destinations"), listType);
                return destinations;
            }
        });
    }

    private interface ApiEndpoint {
        @GET("/destinations")
        Observable<JsonObject> getDestinations();
    }
}
