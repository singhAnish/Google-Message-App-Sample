package com.google.test.message.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.google.test.message.datasource.MessageDataFactory;
import com.google.test.message.datasource.MessageDataSource;
import com.google.test.message.model.Message;
import com.google.test.message.repository.MessageRepository;
import com.google.test.message.utils.NetworkState;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MessageViewModel extends ViewModel {

  private final MessageRepository repository;
  private LiveData<NetworkState> networkState;
  private LiveData<PagedList<Message>> messageLiveData;

  public MessageViewModel(MessageRepository repository) {
    this.repository = repository;
    init();
  }

  @SuppressWarnings("unchecked")
  private void init() {
    MessageDataFactory messageDataFactory = new MessageDataFactory(repository);

    /*networkState = Transformations.switchMap(messageDataFactory.getMutableLiveData(),
        dataSource -> dataSource.getNetworkState());*/

    networkState = Transformations.switchMap(messageDataFactory.getMutableLiveData(),
        (Function<MessageDataSource, LiveData<NetworkState>>) MessageDataSource::getNetworkState);

    PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
        .setEnablePlaceholders(false).setInitialLoadSizeHint(10).setPageSize(10).build();

    Executor executor = Executors.newFixedThreadPool(5);
    messageLiveData = (new LivePagedListBuilder<>(messageDataFactory, pagedListConfig))
        .setFetchExecutor(executor).build();
  }

  public LiveData<NetworkState> getNetworkState() {
    return networkState;
  }

  public LiveData<PagedList<Message>> getMessageLiveData() {
    return messageLiveData;
  }
}
