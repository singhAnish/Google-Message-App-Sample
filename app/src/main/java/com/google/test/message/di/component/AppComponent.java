package com.google.test.message.di.component;

import com.google.test.message.activity.MessageActivity;
import com.google.test.message.di.module.MessageModule;
import com.google.test.message.di.module.app.AppModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { AppModule.class, MessageModule.class })
public interface AppComponent {

    void inject(MessageActivity activity);

}
