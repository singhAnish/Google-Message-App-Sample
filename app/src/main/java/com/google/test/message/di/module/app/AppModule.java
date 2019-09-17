package com.google.test.message.di.module.app;

import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.test.message.BuildConfig;
import com.google.test.message.rest.RestApi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import org.joda.time.DateTime;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module(includes = NetworkModule.class) //include annotation will provide corresponding dependencies
public class AppModule {

  @Provides @Singleton
  public RestApi getMessageApi(Retrofit mRetrofit) {
    return mRetrofit.create(RestApi.class);
  }

  @Provides @Singleton
  public Retrofit getRetrofit(OkHttpClient mOKHttpClient, Gson gson) {
    return new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(mOKHttpClient)
        .baseUrl(BuildConfig.BASE_URL).build();
  }

  @Provides @Singleton
  public Gson getGson() {
    GsonBuilder builder = new GsonBuilder();
    return builder.registerTypeAdapter(DateTime.class, new DateTimeConverter()).create();
  }
}
