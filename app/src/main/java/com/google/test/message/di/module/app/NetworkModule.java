package com.google.test.message.di.module.app;

import android.content.Context;
import com.google.test.message.di.qualifier.AppContext;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module (includes = ContextModule.class)
public class NetworkModule {

    @Provides  @Singleton
    public OkHttpClient getOKHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache mCache ){
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).cache(mCache).build();
    }

    @Provides  @Singleton
    public HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor interceptor =  new HttpLoggingInterceptor(message -> Timber.i( message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return  interceptor;
    }

    @Provides @Singleton
    public Cache getCache(File mFile){
        return new Cache(mFile, 10*1000*1000);
    }

    @Provides @Singleton
    public File getFile(@AppContext Context mContext){
        return new File(mContext.getCacheDir(), "okhttp_cache");
    }
}
