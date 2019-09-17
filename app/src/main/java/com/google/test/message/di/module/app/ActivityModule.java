package com.google.test.message.di.module.app;

import android.app.Activity;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class ActivityModule {

    private final Activity mContext;

    public ActivityModule(Activity context){
        this.mContext = context;
    }

    @Provides
    @Singleton
    @Named("activity_context")//Can also use annotation Qualifier to differentiate between context
    public Context provideContext(){
        return  mContext;
    }

}
