package com.alok.worldoffood.retrofit;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;



import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.alok.worldoffood.utils.Constants.AUTHORIZATION_HEADER;
import static com.alok.worldoffood.utils.Constants.LOGIN_PREFS;
import static com.alok.worldoffood.utils.Constants.LOGIN_TOKEN;


public class RetrofitClient {

    private static Retrofit retrofit;


    private static final String BASE_URL = "https://www.google.com";
    public static final String TAG = RetrofitClient.class.getSimpleName();

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader(AUTHORIZATION_HEADER,
                            "Token " + context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE).getString(LOGIN_TOKEN, ""))
                            .build();
                    Log.d(TAG, "Request is: " + request.body());
                    Log.d("RequestData", context.getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE).getString(LOGIN_TOKEN, ""));
                    return chain.proceed(request);
                }
            });

            httpClient.readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS);



            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }




}
