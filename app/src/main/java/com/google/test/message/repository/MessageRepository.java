package com.google.test.message.repository;

import com.google.test.message.model.MessageSchema;
import com.google.test.message.rest.RestApi;
import retrofit2.Call;

public class MessageRepository {

  private final RestApi messageApi;

  public MessageRepository(RestApi api) {
    messageApi = api;
  }

  public Call<MessageSchema> fetchMessageFromApi(String pageToken) {
    return messageApi.fetchMessage(pageToken);
  }

}
