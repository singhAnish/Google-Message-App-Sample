package com.google.test.message.model;

import java.util.List;

public class MessageSchema {

  private int count;
  private String pageToken;
  private List<Message> messages = null;

  public int getCount() {
    return count;
  }

  public String getPageToken() {
    return pageToken;
  }

  public List<Message> getMessages() {
    return messages;
  }
}