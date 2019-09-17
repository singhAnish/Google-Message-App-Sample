package com.google.test.message.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import com.google.test.message.model.Message;
import com.google.test.message.model.MessageSchema;
import com.google.test.message.repository.MessageRepository;
import com.google.test.message.utils.Constants;
import com.google.test.message.utils.NetworkState;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageDataSource extends PageKeyedDataSource<String, Message> {

  private final MessageRepository repository;
  private final MutableLiveData networkState;
  private final MutableLiveData initialLoading;

  MessageDataSource(MessageRepository repository) {
    this.repository = repository;
    networkState = new MutableLiveData<>();
    initialLoading = new MutableLiveData();
  }

  public MutableLiveData getNetworkState() {
    return networkState;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void loadInitial(@NonNull LoadInitialParams<String> params,
      @NonNull LoadInitialCallback<String, Message> callback) {
    initialLoading.postValue(NetworkState.LOADING);
    networkState.postValue(NetworkState.LOADING);

    repository.fetchMessageFromApi("").enqueue(new Callback<MessageSchema>() {
      @Override
      public void onResponse(@NotNull Call<MessageSchema> call,
          @NotNull Response<MessageSchema> response) {
        if (response.isSuccessful()) {
          if (response.body() != null) {
            MessageSchema schema = response.body();
            callback.onResult(response.body().getMessages(), "", schema.getPageToken());
            initialLoading.postValue(NetworkState.LOADED);
            networkState.postValue(NetworkState.LOADED);
          } else {
            networkState.postValue(
                new NetworkState(NetworkState.Status.FAILED, Constants.SOMETHING_WRONG));
          }
        } else {
          initialLoading.postValue(
              new NetworkState(NetworkState.Status.FAILED, response.message()));
          networkState.postValue(
              new NetworkState(NetworkState.Status.FAILED, response.message()));
        }
      }

      @Override
      public void onFailure(@NotNull Call<MessageSchema> call, @NotNull Throwable t) {
        String errorMessage = t.getMessage();
        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
      }
    });
  }

  @Override public void loadBefore(@NonNull LoadParams<String> params,
      @NonNull LoadCallback<String, Message> callback) {

  }

  @SuppressWarnings("unchecked")
  @Override
  public void loadAfter(@NonNull LoadParams<String> params,
      @NonNull LoadCallback<String, Message> callback) {
    if (params.key != null && !params.key.isEmpty()) {
      networkState.postValue(NetworkState.LOADING);
      repository.fetchMessageFromApi(params.key).enqueue(new Callback<MessageSchema>() {
        @SuppressWarnings("unchecked")
        @Override
        public void onResponse(@NotNull Call<MessageSchema> call,
            @NotNull Response<MessageSchema> response) {
          if (response.isSuccessful()) {
            if (response.body() != null) {
              MessageSchema schema = response.body();
              callback.onResult(response.body().getMessages(), schema.getPageToken());
              networkState.postValue(NetworkState.LOADED);
            } else {
              networkState.postValue(
                  new NetworkState(NetworkState.Status.FAILED, Constants.SOMETHING_WRONG));
            }
          } else {
            networkState.postValue(
                new NetworkState(NetworkState.Status.FAILED, response.message()));
          }
        }

        @Override
        public void onFailure(@NotNull Call<MessageSchema> call, @NotNull Throwable t) {
          String errorMessage = t.getMessage();
          networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
        }
      });
    }
  }
}
