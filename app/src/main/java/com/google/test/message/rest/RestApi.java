package com.google.test.message.rest;

import com.google.test.message.model.MessageSchema;
import com.google.test.message.utils.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

  @GET(Constants.MESSAGES)
  Call<MessageSchema> fetchMessage(@Query(Constants.PAGE_TOKEN) String page_token);

}
