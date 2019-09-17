package com.google.test.message.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.google.test.message.repository.MessageRepository;
import com.google.test.message.utils.Constants;
import com.google.test.message.viewmodel.MessageViewModel;
import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

  private final MessageRepository repository;

  @Inject
  public ViewModelFactory(MessageRepository repository) {
    this.repository = repository;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(MessageViewModel.class)) {
      return (T) new MessageViewModel(repository);
    }
    throw new IllegalArgumentException(Constants.UNKNOWN_CLASS);
  }
}