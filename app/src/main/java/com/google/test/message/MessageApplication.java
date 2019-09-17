package com.google.test.message;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import com.google.test.message.di.component.AppComponent;
import com.google.test.message.di.component.DaggerAppComponent;
import com.google.test.message.di.module.app.ContextModule;
import com.google.test.message.utils.ConnectionDetector;
import timber.log.Timber;

public class MessageApplication extends Application {

  private AppComponent component;

  public static MessageApplication get(Activity activity) {
    return (MessageApplication) activity.getApplication();
  }

  @Override
  public void onCreate() {
    super.onCreate();
    this.registerReceiver(ConnectionDetector.getDetector().new NetworkReceiver(), new IntentFilter(
        ConnectivityManager.CONNECTIVITY_ACTION));
    ConnectionDetector.getDetector().initConnectionState(this);

    Timber.plant(new Timber.DebugTree());
    component = DaggerAppComponent.builder().contextModule(new ContextModule(this)).build();
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    component = null;
  }

  public AppComponent getComponent() {
    return component;
  }

  private static MessageApplication get(Context context) {
    return (MessageApplication) context.getApplicationContext();
  }

  public static MessageApplication create(Context context) {
    return MessageApplication.get(context);
  }
}
