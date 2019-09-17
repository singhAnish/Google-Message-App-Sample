package com.google.test.message.di.module.app;

import android.content.Context;
import com.google.test.message.di.qualifier.AppContext;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ContextModule {

    private final Context mContext;

    public ContextModule(Context context){
        this.mContext = context;
    }

    @Provides
    @Singleton
    @AppContext //can also use Named annotation to differentiate between context
    public Context provideContext(){
        return  mContext;
    }

}
