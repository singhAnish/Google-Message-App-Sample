package com.google.test.message.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.test.message.BuildConfig;
import com.google.test.message.R;
import com.google.test.message.databinding.MessageItemBinding;
import com.google.test.message.databinding.NetworkItemBinding;
import com.google.test.message.model.Message;
import com.google.test.message.utils.Constants;
import com.google.test.message.utils.NetworkState;
import java.util.Objects;

public class MessageAdapter extends PagedListAdapter<Message, RecyclerView.ViewHolder> {

  private static final int TYPE_PROGRESS = 0;
  private static final int TYPE_ITEM = 1;

  private NetworkState networkState;
  private final RequestOptions options;

  public MessageAdapter() {
    super(Message.DIFF_CALLBACK);
    options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    if (viewType == TYPE_PROGRESS) {
      NetworkItemBinding headerBinding = NetworkItemBinding.inflate(layoutInflater, parent, false);
      return new NetworkStateItemViewHolder(headerBinding);
    } else {
      MessageItemBinding itemBinding = MessageItemBinding.inflate(layoutInflater, parent, false);
      return new ItemViewHolder(itemBinding);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof ItemViewHolder) {
      ((ItemViewHolder) holder).bindTo(Objects.requireNonNull(getItem(position)));
    } else {
      ((NetworkStateItemViewHolder) holder).bindView(networkState);
    }
  }

  private boolean hasExtraRow() {
    return networkState != null && networkState != NetworkState.LOADED;
  }

  @Override
  public int getItemViewType(int position) {
    if (hasExtraRow() && position == getItemCount() - 1) {
      return TYPE_PROGRESS;
    } else {
      return TYPE_ITEM;
    }
  }

  public void setNetworkState(NetworkState newNetworkState) {
    NetworkState previousState = this.networkState;
    boolean previousExtraRow = hasExtraRow();
    this.networkState = newNetworkState;
    boolean newExtraRow = hasExtraRow();
    if (previousExtraRow != newExtraRow) {
      if (previousExtraRow) {
        notifyItemRemoved(getItemCount());
      } else {
        notifyItemInserted(getItemCount());
      }
    } else if (newExtraRow && previousState != newNetworkState) {
      notifyItemChanged(getItemCount() - 1);
    }
  }

  private class ItemViewHolder extends RecyclerView.ViewHolder {
    private final MessageItemBinding binding;

    private ItemViewHolder(MessageItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bindTo(Message msg) {
      binding.userName.setText(msg.getAuthor().getName());
      binding.userContent.setText(msg.getContent());
      binding.userMessageTime.setText(msg.getUpdated());
      Glide.with(binding.userIcon).load(BuildConfig.BASE_URL + msg.getAuthor().getPhotoUrl())
          .apply(options).thumbnail(0.1f).into(binding.userIcon);
    }
  }

  private class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {
    private final NetworkItemBinding binding;

    private NetworkStateItemViewHolder(NetworkItemBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void bindView(NetworkState networkState) {
      if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
        binding.progressBar.setVisibility(View.VISIBLE);
      } else {
        binding.progressBar.setVisibility(View.GONE);
      }

      if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
        binding.errorMsg.setText(networkState.getMsg());
      } else {
        binding.errorMsg.setText(Constants.LOADING);
      }
    }
  }
}
