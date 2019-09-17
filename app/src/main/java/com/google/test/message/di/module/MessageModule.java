package com.google.test.message.di.module;

import androidx.lifecycle.ViewModelProvider;
import com.google.test.message.repository.MessageRepository;
import com.google.test.message.rest.RestApi;
import com.google.test.message.viewmodel.factory.ViewModelFactory;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class MessageModule {

    @Provides
    @Singleton
    MessageRepository getRepository(RestApi messageApi) {
        return new MessageRepository(messageApi);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(MessageRepository repository) {
        return new ViewModelFactory(repository);
    }

}
