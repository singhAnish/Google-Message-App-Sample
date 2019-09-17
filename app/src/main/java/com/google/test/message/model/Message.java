package com.google.test.message.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Message {

  private int id;
  private String content;
  private String updated;
  private Author author;

  public int getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public String getUpdated() {
    return updated;
  }

  public Author getAuthor() {
    return author;
  }

  public static final DiffUtil.ItemCallback<Message> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<Message>() {
        @Override
        public boolean areItemsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
          return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Message oldItem, @NonNull Message newItem) {
          return oldItem.equals(newItem);
        }
      };

  @SuppressWarnings("EqualsWhichDoesntCheckParameterClass") @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    Message message = (Message) obj;
    return message.id == this.id;
  }
}