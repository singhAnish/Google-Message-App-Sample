package com.google.test.message.utils;

public class NetworkState {

  public enum Status {
    RUNNING,
    SUCCESS,
    FAILED
  }

  static {
    LOADED = new NetworkState(Status.SUCCESS, Constants.SUCCESS);
    LOADING = new NetworkState(Status.RUNNING, Constants.RUNNING);
  }

  private final Status status;
  private final String msg;
  public static final NetworkState LOADED;
  public static final NetworkState LOADING;

  public NetworkState(Status status, String msg) {
    this.status = status;
    this.msg = msg;
  }

  public Status getStatus() {
    return status;
  }

  public String getMsg() {
    return msg;
  }
}
