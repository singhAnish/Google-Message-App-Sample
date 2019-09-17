package com.google.test.message.activity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.test.message.MessageApplication;
import com.google.test.message.R;
import com.google.test.message.adapters.MessageAdapter;
import com.google.test.message.databinding.ActivityMessageBinding;
import com.google.test.message.viewmodel.MessageViewModel;
import com.google.test.message.viewmodel.factory.ViewModelFactory;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

public class MessageActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  @Inject ViewModelFactory viewModelFactory;
  private MessageAdapter adapter;
  private ActivityMessageBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_message);

    setSupportActionBar(binding.appBar.toolbar);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, binding.drawerLayout, binding.appBar.toolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close);
    binding.drawerLayout.addDrawerListener(toggle);
    toggle.syncState();
    binding.navView.setNavigationItemSelectedListener(this);
    init();
  }

  private void init() {
    ((MessageApplication) getApplication()).getComponent().inject(this);
    MessageViewModel viewModel =
        ViewModelProviders.of(this, viewModelFactory).get(MessageViewModel.class);

    binding.appBar.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new MessageAdapter();
    binding.appBar.recyclerView.setAdapter(adapter);

    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
      @Override public boolean onMove(@NonNull RecyclerView recyclerView,
          @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
      }
    }).attachToRecyclerView(binding.appBar.recyclerView);

   /* viewModel.getMessageLiveData().observe(this, pagedList -> {adapter.submitList(pagedList); });
    viewModel.getNetworkState().observe(this, networkState -> adapter.setNetworkState(networkState));*/

    viewModel.getMessageLiveData().observe(this, adapter :: submitList);
    viewModel.getNetworkState().observe(this, adapter :: setNetworkState);
  }

  @Override
  public void onBackPressed() {
    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
      binding.drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NotNull MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.nav_home) {
      showSnackBar();
    } else if (id == R.id.nav_gallery) {
      showSnackBar();
    } else if (id == R.id.nav_slideshow) {
      showSnackBar();
    } else if (id == R.id.nav_tools) {
      showSnackBar();
    } else if (id == R.id.nav_share) {
      showSnackBar();
    } else if (id == R.id.nav_send) {
      showSnackBar();
    }
    binding.drawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }

  private void showSnackBar() {
    Snackbar.make(binding.drawerLayout, R.string.no_functionality, Snackbar.LENGTH_SHORT).show();
  }
}
