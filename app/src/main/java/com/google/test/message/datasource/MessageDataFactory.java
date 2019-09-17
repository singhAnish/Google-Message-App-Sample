package com.google.test.message.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import com.google.test.message.repository.MessageRepository;

public class MessageDataFactory extends DataSource.Factory {

    private final MutableLiveData<MessageDataSource> mutableLiveData;
    private final MessageRepository repository;

    public MessageDataFactory(MessageRepository repository) {
        this.repository = repository;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        MessageDataSource messageDataSource = new MessageDataSource(repository);
        mutableLiveData.postValue(messageDataSource);
        return messageDataSource;
    }

    public MutableLiveData<MessageDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
